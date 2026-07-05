package com.icmon.module.dashboard.infrastructure.query;

import com.icmon.module.dashboard.domain.*;
import com.icmon.module.dashboard.domain.valueobjects.DateRange;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DashboardNativeQuery {

    private final EntityManager entityManager;

    public Long countInvoices(UUID whitelabelId) {
        Query q = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM t_invoice_adjustment WHERE deleted = false AND type = 'INVOICE' AND whitelabel_id = ?1");
        q.setParameter(1, whitelabelId.toString());
        return ((Number) q.getSingleResult()).longValue();
    }

    public BigDecimal sumRevenue(UUID whitelabelId) {
        Query q = entityManager.createNativeQuery(
            "SELECT COALESCE(SUM(total), 0) FROM t_invoice_adjustment WHERE deleted = false AND type = 'INVOICE' AND whitelabel_id = ?1");
        q.setParameter(1, whitelabelId.toString());
        return ((Number) q.getSingleResult()) instanceof BigDecimal bd ? bd : BigDecimal.valueOf(((Number) q.getSingleResult()).doubleValue());
    }

    public BigDecimal sumOutstanding(UUID whitelabelId) {
        Query q = entityManager.createNativeQuery(
            "SELECT COALESCE(SUM(total - COALESCE((SELECT SUM(amount) FROM t_payment WHERE invoice_id = i.id AND status = 'COMPLETED'), 0)), 0) " +
            "FROM t_invoice_adjustment i WHERE i.deleted = false AND i.type = 'INVOICE' AND i.whitelabel_id = ?1");
        q.setParameter(1, whitelabelId.toString());
        return ((Number) q.getSingleResult()) instanceof BigDecimal bd ? bd : BigDecimal.valueOf(((Number) q.getSingleResult()).doubleValue());
    }

    public Long countJobs(UUID whitelabelId) {
        Query q = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM t_job WHERE deleted = false AND whitelabel_id = ?1");
        q.setParameter(1, whitelabelId.toString());
        return ((Number) q.getSingleResult()).longValue();
    }

    public Long countCustomers(UUID whitelabelId) {
        Query q = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM m_customer WHERE deleted = false AND whitelabel_id = ?1");
        q.setParameter(1, whitelabelId.toString());
        return ((Number) q.getSingleResult()).longValue();
    }

    public Long countLowStock(UUID whitelabelId) {
        Query q = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM m_part_master WHERE deleted = false AND stock_quantity <= reorder_level AND whitelabel_id = ?1");
        q.setParameter(1, whitelabelId.toString());
        return ((Number) q.getSingleResult()).longValue();
    }

    public Long countPayments(UUID whitelabelId) {
        Query q = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM t_payment WHERE deleted = false AND status = 'COMPLETED' AND whitelabel_id = ?1");
        q.setParameter(1, whitelabelId.toString());
        return ((Number) q.getSingleResult()).longValue();
    }

    public DSalesOverview getSalesOverview(String period, DateRange dateRange, UUID whitelabelId) {
        String sql = "SELECT COUNT(DISTINCT id) AS total_invoices, COALESCE(SUM(total), 0) AS total_revenue " +
                     "FROM t_invoice_adjustment WHERE deleted = false AND type = 'INVOICE' " +
                     "AND invoice_date >= ?1 AND invoice_date <= ?2 AND whitelabel_id = ?3";
        Query q = entityManager.createNativeQuery(sql);
        q.setParameter(1, java.sql.Date.valueOf(dateRange.startDate()));
        q.setParameter(2, java.sql.Date.valueOf(dateRange.endDate()));
        q.setParameter(3, whitelabelId.toString());
        Object[] row = (Object[]) q.getSingleResult();
        DSalesOverview overview = new DSalesOverview();
        overview.setTotalInvoices(((Number) row[0]).intValue());
        overview.setTotalRevenue(((Number) row[1]) instanceof BigDecimal bd ? bd : BigDecimal.valueOf(((Number) row[1]).doubleValue()));
        return overview;
    }

    @SuppressWarnings("unchecked")
    public List<DJobStatusSummary> getJobStatusSummary(UUID whitelabelId) {
        Query q = entityManager.createNativeQuery(
            "SELECT status, COUNT(*) AS cnt FROM t_job WHERE deleted = false AND whitelabel_id = ?1 GROUP BY status");
        q.setParameter(1, whitelabelId.toString());
        List<Object[]> rows = q.getResultList();
        List<DJobStatusSummary> result = new ArrayList<>();
        for (Object[] row : rows) {
            DJobStatusSummary s = new DJobStatusSummary();
            s.setStatus((String) row[0]);
            s.setCount(((Number) row[1]).longValue());
            s.setWhitelabelId(whitelabelId);
            result.add(s);
        }
        return result;
    }

    public DInventoryOverview getInventoryOverview(UUID whitelabelId) {
        String sql = "SELECT COUNT(*) AS total_parts, COALESCE(SUM(stock_quantity), 0) AS total_qty, " +
                     "COALESCE(SUM(stock_quantity * unit_cost), 0) AS total_value, " +
                     "COUNT(CASE WHEN stock_quantity <= reorder_level THEN 1 END) AS low_stock, " +
                     "COUNT(CASE WHEN status = 'ACTIVE' THEN 1 END) AS active " +
                     "FROM m_part_master WHERE deleted = false AND whitelabel_id = ?1";
        Query q = entityManager.createNativeQuery(sql);
        q.setParameter(1, whitelabelId.toString());
        Object[] row = (Object[]) q.getSingleResult();
        DInventoryOverview overview = new DInventoryOverview();
        overview.setTotalParts(((Number) row[0]).longValue());
        overview.setTotalQuantity(((Number) row[1]).longValue());
        overview.setTotalValue(((Number) row[2]) instanceof BigDecimal bd ? bd : BigDecimal.valueOf(((Number) row[2]).doubleValue()));
        overview.setLowStockCount(((Number) row[3]).longValue());
        overview.setActiveParts(((Number) row[4]).longValue());
        overview.setWhitelabelId(whitelabelId);
        return overview;
    }

    @SuppressWarnings("unchecked")
    public List<DTopSellingParts> getTopParts(int limit, UUID whitelabelId) {
        String sql = "SELECT p.id, p.part_code, p.part_name, COALESCE(SUM(jps.quantity), 0) AS total_sold, " +
                     "COALESCE(SUM(jps.net_price), 0) AS total_revenue " +
                     "FROM m_part_master p LEFT JOIN t_job_part_sales jps ON jps.part_id = p.id " +
                     "WHERE p.deleted = false AND p.whitelabel_id = ?1 " +
                     "GROUP BY p.id, p.part_code, p.part_name ORDER BY total_sold DESC LIMIT ?2";
        Query q = entityManager.createNativeQuery(sql);
        q.setParameter(1, whitelabelId.toString());
        q.setParameter(2, limit);
        List<Object[]> rows = q.getResultList();
        List<DTopSellingParts> result = new ArrayList<>();
        int rank = 1;
        for (Object[] row : rows) {
            DTopSellingParts p = new DTopSellingParts();
            p.setPartId(java.util.UUID.fromString((String) row[0]));
            p.setPartCode((String) row[1]);
            p.setPartName((String) row[2]);
            p.setTotalSold(((Number) row[3]).longValue());
            p.setTotalRevenue(((Number) row[4]) instanceof BigDecimal bd ? bd : BigDecimal.valueOf(((Number) row[4]).doubleValue()));
            p.setRank(rank++);
            p.setWhitelabelId(whitelabelId);
            result.add(p);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<DServiceCategory> getServiceCategory(UUID whitelabelId) {
        String sql = "SELECT c.name, COUNT(js.id) AS service_count, COALESCE(SUM(js.net_price), 0) AS total_revenue " +
                     "FROM t_job_service js JOIN m_service s ON s.id = js.service_id " +
                     "JOIN m_category c ON c.id = s.category_id " +
                     "WHERE js.deleted = false AND js.whitelabel_id = ?1 GROUP BY c.name";
        Query q = entityManager.createNativeQuery(sql);
        q.setParameter(1, whitelabelId.toString());
        List<Object[]> rows = q.getResultList();
        List<DServiceCategory> result = new ArrayList<>();
        for (Object[] row : rows) {
            DServiceCategory sc = new DServiceCategory();
            sc.setCategoryName((String) row[0]);
            sc.setServiceCount(((Number) row[1]).longValue());
            sc.setTotalRevenue(((Number) row[2]) instanceof BigDecimal bd ? bd : BigDecimal.valueOf(((Number) row[2]).doubleValue()));
            sc.setWhitelabelId(whitelabelId);
            result.add(sc);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<DRevenueByPeriod> getRevenueByPeriod(String period, int months, UUID whitelabelId) {
        String trunc = switch (period.toUpperCase()) {
            case "DAY" -> "day";
            case "YEAR" -> "year";
            default -> "month";
        };
        String sql = "SELECT DATE_TRUNC('" + trunc + "', invoice_date) AS period, COUNT(*) AS cnt, " +
                     "COALESCE(SUM(total), 0) AS revenue, COALESCE(AVG(total), 0) AS avg_rev " +
                     "FROM t_invoice_adjustment WHERE deleted = false AND type = 'INVOICE' " +
                     "AND invoice_date >= CURRENT_DATE - INTERVAL '1 month' * ?1 AND whitelabel_id = ?2 " +
                     "GROUP BY period ORDER BY period";
        Query q = entityManager.createNativeQuery(sql);
        q.setParameter(1, months);
        q.setParameter(2, whitelabelId.toString());
        List<Object[]> rows = q.getResultList();
        List<DRevenueByPeriod> result = new ArrayList<>();
        for (Object[] row : rows) {
            DRevenueByPeriod r = new DRevenueByPeriod();
            r.setPeriod(((java.sql.Date) row[0]).toLocalDate());
            r.setInvoiceCount(((Number) row[1]).intValue());
            r.setRevenue(((Number) row[2]) instanceof BigDecimal bd ? bd : BigDecimal.valueOf(((Number) row[2]).doubleValue()));
            r.setAverageRevenue(((Number) row[3]) instanceof BigDecimal bd ? bd : BigDecimal.valueOf(((Number) row[3]).doubleValue()));
            r.setWhitelabelId(whitelabelId);
            result.add(r);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<DFinancialSummary> getFinancialSummary(DateRange dateRange, UUID whitelabelId) {
        String sql = "SELECT DATE_TRUNC('month', invoice_date) AS month, COALESCE(SUM(total), 0) AS total_inv " +
                     "FROM t_invoice_adjustment WHERE deleted = false AND type = 'INVOICE' " +
                     "AND invoice_date >= ?1 AND invoice_date <= ?2 AND whitelabel_id = ?3 " +
                     "GROUP BY month ORDER BY month";
        Query q = entityManager.createNativeQuery(sql);
        q.setParameter(1, java.sql.Date.valueOf(dateRange.startDate()));
        q.setParameter(2, java.sql.Date.valueOf(dateRange.endDate()));
        q.setParameter(3, whitelabelId.toString());
        List<Object[]> rows = q.getResultList();
        List<DFinancialSummary> result = new ArrayList<>();
        for (Object[] row : rows) {
            DFinancialSummary fs = new DFinancialSummary();
            fs.setMonth(((java.sql.Date) row[0]).toLocalDate());
            fs.setTotalInvoice(((Number) row[1]) instanceof BigDecimal bd ? bd : BigDecimal.valueOf(((Number) row[1]).doubleValue()));
            fs.setTotalPayment(BigDecimal.ZERO);
            fs.setNetIncome(fs.getTotalInvoice());
            fs.setWhitelabelId(whitelabelId);
            result.add(fs);
        }
        return result;
    }
}
