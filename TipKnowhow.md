
ใน Java Spring Boot **`@Autowired`** คือหนึ่งใน Annotation ที่สำคัญที่สุดและถูกใช้งานบ่อยที่สุดครับ โดยหน้าที่หลักของมันคือการทำ **Dependency Injection (DI)** หรือพูดง่ายๆ คือ **"การสั่งให้ Spring Boot ค้นหาและนำ Object (Bean) ที่เหมาะสมมาใส่ให้เราโดยอัตโนมัติ"** โดยที่เราไม่ต้องไปสั่ง `new Object()` เองในโค้ด

เพื่อให้เข้าใจง่ายขึ้น เรามาแบ่งอธิบายเป็นหัวข้อตามคำถามของคุณดังนี้ครับ:

---

### 1. `@Autowired` คืออะไร และมีไว้ทำไม?

ในอดีต (หรือการเขียน Java ทั่วไปที่ไม่ใช้ Framework) เวลาเราต้องการใช้งาน Class หนึ่งในอีก Class หนึ่ง เราจะต้องสร้าง Object ขึ้นมาเอง เช่น:

```java
// แบบดั้งเดิม (ไม่ได้ใช้ Spring)
public class CarService {
    private CarRepository carRepository = new CarRepository(); // ต้องสร้างเอง ผูกมัดกันแน่น (Tight Coupling)
}

```

**ปัญหาคือ:** หาก `CarRepository` มีการเปลี่ยนแปลง หรือต้องการสลับไปใช้ Database อื่น โค้ดของเราจะแก้ไขได้ยาก และทำ Unit Test ได้ลำบาก

**เมื่อมี `@Autowired` (ระบบ Spring):**
Spring Framework จะทำหน้าที่เป็น "คนกลาง" ในการสร้างและจัดการ Object ต่างๆ (ซึ่งใน Spring เราจะเรียกว่า **Bean**) เอาไว้ในกล่องส่วนกลาง (Spring Container)

เมื่อเราใส่ `@Autowired` ไว้ที่จุดใดก็ตาม มันเหมือนเป็นการบอก Spring ว่า **"เฮ้ย Spring! ฉันจำเป็นต้องใช้ Object ชิ้นนี้ใน Class นี้นะ ช่วยไปหยิบจากในกล่องมาใส่ให้หน่อย"**

---

### 2. `@Autowired` ใช้ทำอะไร? (ตัวอย่างการใช้งาน)

เรามักจะใช้ `@Autowired` ในการเชื่อมต่อ Layer ต่างๆ เข้าด้วยกัน เช่น เชื่อมจาก **Controller** ไปหา **Service** และจาก **Service** ไปหา **Repository**

ตัวอย่างจากระบบบริหารจัดการอู่ซ่อมรถ:

```java
@Service
public class CarService {

    // สั่งให้ Spring หยิบ CarRepository ที่พร้อมใช้งานมาใส่ให้โดยอัตโนมัติ
    @Autowired
    private CarRepository carRepository; 

    public List<Car> getAllCars() {
        return carRepository.findAll(); // เรียกใช้งานได้ทันที ไม่ต้อง "new CarRepository()" เอง
    }
}

```

---

### 3. รูปแบบการใช้ `@Autowired` (มี 3 แบบหลักๆ)

เราสามารถแปะ `@Autowired` ไว้ได้ 3 จุดหลักๆ ใน Class ครับ:

#### แบบที่ 1: Field Injection (แปะไว้ที่ตัวแปรโดยตรง)

*วิธีนี้เขียนสั้นและนิยมที่สุดในอดีต (เหมือนตัวอย่างข้างบน)*

```java
@Autowired
private CarRepository carRepository;

```

#### แบบที่ 2: Constructor Injection (แปะไว้ที่ Constructor)

***นี่เป็นวิธีที่ Spring แนะนำมากที่สุดในปัจจุบัน (Best Practice)*** เนื่องจากทำให้โค้ดปลอดภัย ทำ Unit Test ง่าย และถ้ามี Constructor เดียวในเวอร์ชันใหม่ๆ เราสามารถ**ละการเขียนคำว่า `@Autowired` ได้เลย** Spring จะเข้าใจและจัดการให้เอง

```java
private final CarRepository carRepository;

@Autowired // ใส่หรือไม่ใส่ก็ได้ถ้ามี Constructor เดียว
public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
}

```

#### แบบที่ 3: Setter Injection (แปะไว้ที่ Method Setter)

*ใช้ในกรณีที่ Object นั้นๆ สามารถเปลี่ยนแปลงได้ในภายหลัง หรือเป็น Optional*

```java
private CarRepository carRepository;

@Autowired
public void setCarRepository(CarRepository carRepository) {
    this.carRepository = carRepository;
}

```

---

### สรุปประโยชน์ของ `@Autowired`

1. **ลดภาระในการสร้าง Object (`new`):** ปล่อยให้ Spring บริหารจัดการ Life Cycle ของ Object ให้ทั้งหมด
2. **ลดความผูกมัดของโค้ด (Loose Coupling):** ทำให้ Class ต่างๆ ไม่ยึดติดกันจนเกินไป สามารถปรับเปลี่ยนโครงสร้างภายในได้ง่าย
3. **เขียน Unit Test ได้ง่ายขึ้น:** เราสามารถจำลอง (Mock) Object ตัวอื่น แล้วส่งเข้าไปแทนที่ผ่าน Constructor ได้ง่ายมาก

 


# อธิบาย @Override และ @Transactional แบบละเอียด

ในระบบ Spring Boot ที่เรากำลังพัฒนาร่วมกัน (ระบบบริหารจัดการอู่ซ่อมรถ) เราจะพบเห็น Annotation ทั้งสองนี้บ่อยมากใน Layer ต่างๆ โดยเฉพาะ Service Layer และ Repository Layer

---

## 1. @Override (ในภาษา Java)

### 1.1 ความหมายและจุดประสงค์

**`@Override`** คือ **Annotation เครื่องหมาย (Marker Annotation)** ที่ใช้บอกคอมไพเลอร์ (Compiler) ว่า **เมธอดนี้กำลัง "แทนที่" (Override) เมธอดของคลาสแม่ (Superclass) หรือกำลัง "implement" เมธอดจาก Interface**

มันไม่ใช่คำสั่งบังคับให้ทำงาน (ไม่ใช่ Logic) แต่เป็น **สัญญาณ (Hint)** ให้กับ Compiler และนักพัฒนาคนอื่นๆ

### 1.2 ทำไมต้องใช้?

| ประโยชน์ | คำอธิบาย |
|---------|----------|
| **ตรวจสอบข้อผิดพลาดตั้งแต่ตอน Compile** | ถ้าเราเขียนชื่อเมธอดผิด หรือพารามิเตอร์ผิด Compiler จะแจ้ง Error ทันที เพราะมันจะไปหาเมธอดในคลาสแม่ไม่เจอ |
| **เพิ่มความอ่านง่าย (Readability)** | ทำให้คนอ่านโค้ดรู้ทันทีว่าเมธอดนี้ถูกดัดแปลงมาจากที่ไหน (ไม่ใช่เมธอดใหม่ของคลาสนี้) |
| **ป้องกันการเกิด Bug ซ่อนเร้น** | ป้องกันการเขียนเมธอดใหม่โดยไม่ตั้งใจ (Overload) แทนที่จะ Override |

### 1.3 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: Service Layer (Generic → Specific)
```java
// ใน GenericServiceImpl (คลาสแม่)
public abstract class GenericServiceImpl<E, R> {
    public E read(UUID id) { /* ... */ }
}

// ใน JobServiceImpl (คลาสลูก) - ต้องการ Override เมธอด read
@Service
public class JobServiceImpl extends GenericServiceImpl<TJob, JobRepository> {
    
    @Override  // ✅ ใช้ @Override เพื่อบอกว่าเรากำลังแก้ไขเมธอด read ของคลาสแม่
    public E read(UUID id) throws SystemGlobalException {
        // เพิ่ม Business Logic พิเศษก่อนอ่านข้อมูล Job
        log.info("Reading job with custom logic");
        return super.read(id); // เรียกของคลาสแม่ต่อ
    }
}
```
> ถ้าลบ `@Override` ออก แล้วเผลอเขียนเป็น `public E reed(UUID id)` (สะกดผิด) มันจะไม่เกิด Error แต่จะกลายเป็นเมธอดใหม่ของคลาสนี้ และระบบจะไม่เรียกใช้เมธอดนี้ เพราะ Spring จะเรียกเมธอดของคลาสแม่แทน ทำให้เกิด Bug หายาก!

#### ตัวอย่างที่ 2: Interface Implementation
```java
// ใน Controller
@RestController
public class JobController {
    
    @Override  // ✅ บอกว่าเรากำลัง Implement เมธอดจาก Interface ที่สืบทอดมา
    public ResponseEntity<JobResponseDTO> createJob(@RequestBody JobCreateRequestDTO request) {
        // ...
    }
}
```

### 1.4 สรุปสั้นๆ
> `@Override` คือการ "เซ็นชื่อ" บนเมธอดว่า "เมธอดนี้มีอยู่แล้วในพ่อแม่ ผมขอแก้ไข/เติมเต็มมันนะ" ถ้าเซ็นชื่อผิด (ไม่มีเมธอดนั้นในพ่อแม่) Compiler จะบอกให้แก้ทันที

---

## 2. @Transactional (ใน Spring Framework)

### 2.1 ความหมายและจุดประสงค์

**`@Transactional`** คือ Annotation ที่ใช้ **จัดการขอบเขตของ Transaction (ธุรกรรม) ในฐานข้อมูล** โดยอัตโนมัติ

> ในระบบฐานข้อมูล (โดยเฉพาะ SQL) "Transaction" คือ กลุ่มของคำสั่ง SQL ที่ต้องทำงานสำเร็จ **ทั้งหมด** หรือ **ไม่ทำงานเลย** (Atomicity) เช่น การโอนเงิน: ต้องหักเงินจากบัญชี A **และ** เพิ่มเงินให้บัญชี B ถ้าตัวใดตัวหนึ่งล้มเหลว ทั้งสองอย่างต้องไม่เกิดขึ้น

### 2.2 ทำงานอย่างไร? (Behind the Scenes)

Spring ใช้ **AOP (Aspect-Oriented Programming)** และ **Dynamic Proxy** ในการทำงาน:

1. **Proxy ถูกสร้างขึ้น** : Spring จะสร้าง "ตัวแทน" (Proxy Object) ของคลาสที่ถูก `@Transactional`
2. **เปิด Transaction** : เมื่อเรียกเมธอด `@Transactional` Proxy จะเปิด Transaction (BEGIN) ก่อน
3. **รันเมธอดจริง** : เรียกเมธอดจริงของเรา
4. **Commit หรือ Rollback** :
   - ถ้าเมธอดทำงานสำเร็จ → `COMMIT` (บันทึกข้อมูลจริง)
   - ถ้าเกิด `RuntimeException` หรือ `Error` → `ROLLBACK` (ย้อนกลับข้อมูลทั้งหมด)

### 2.3 พารามิเตอร์ที่สำคัญ (รายละเอียด)

| พารามิเตอร์ | ค่าเริ่มต้น | คำอธิบาย |
|-----------|-----------|----------|
| **`propagation`** | `Propagation.REQUIRED` | เมธอดนี้ต้องทำงานภายใต้ Transaction ถ้ามีอยู่แล้วก็ใช้ร่วมกัน ถ้าไม่มีก็สร้างใหม่ |
| **`isolation`** | `Isolation.DEFAULT` | ระดับการแยกข้อมูลระหว่าง Transaction ที่ทำงานพร้อมกัน (อ่านค่า Dirty Read, Phantom Read) |
| **`rollbackFor`** | `RuntimeException` และ `Error` | ระบุ Exception ที่จะทำให้เกิด Rollback (เรามักใช้ `Exception.class` เพื่อให้ Rollback ทุกตัว) |
| **`readOnly`** | `false` | `true` = เป็นการอ่านข้อมูลอย่างเดียว (ปรับปรุงประสิทธิภาพ) |
| **`timeout`** | -1 (ไม่มี limit) | กำหนดเวลาสูงสุด (วินาที) ถ้าเกินจะ Rollback อัตโนมัติ |

### 2.4 ตัวอย่างในระบบของเรา (ใช้จริง)

#### ตัวอย่างที่ 1: การสร้าง Job (ต้องบันทึกหลายตาราง)
```java
@Service
public class JobServiceImpl extends GenericServiceImpl<TJob, JobRepository> {

    @Override
    @Transactional(rollbackFor = Exception.class)  // ✅ ถ้ามี Error อะไรก็ตาม ให้ Rollback ทั้งหมด
    public TJob createJob(JobCreateRequestDTO request) throws SystemGlobalException {
        // 1. บันทึก Job หลัก
        TJob job = new TJob();
        job.setCustomerId(request.getCustomerId());
        TJob savedJob = repository.save(job);
        
        // 2. บันทึก Service Items (ถ้ามี)
        for (ServiceItem item : request.getServices()) {
            TJobService jobService = new TJobService();
            jobService.setJobId(savedJob.getId());
            jobServiceRepository.save(jobService);  // ถ้าตัวนี้ Error -> Rollback ข้อ 1 ด้วย!
        }
        
        // 3. บันทึก Part Sales (ถ้ามี)
        for (PartItem item : request.getParts()) {
            TJobPartSales partSale = new TJobPartSales();
            partSale.setJobId(savedJob.getId());
            partSaleRepository.save(partSale);      // ถ้าตัวนี้ Error -> Rollback ข้อ 1,2!
        }
        
        return savedJob;
    }
}
```
> ถ้าไม่มี `@Transactional` และเกิด Error ที่ขั้นตอนที่ 3 ข้อมูลในขั้นตอนที่ 1 และ 2 จะถูกบันทึกไปแล้วครึ่งเดียว ทำให้ข้อมูลเสีย (Inconsistent Data)

#### ตัวอย่างที่ 2: การอนุมัติ Quotation (สร้าง PO และลดสต็อก)
```java
@Service
public class QuotationServiceImpl {
    
    @Transactional(rollbackFor = Exception.class, timeout = 30) // ✅ หมดเขต 30 วินาที
    public void approveQuotation(UUID quotationId) {
        // 1. อัปเดตสถานะ Quotation
        quotationRepository.updateStatus(quotationId, "APPROVED");
        
        // 2. สร้าง Purchase Order
        purchaseOrderService.createFromQuotation(quotationId);
        
        // 3. ลดจำนวนสต็อก (Inventory)
        inventoryService.reduceStock(quotationId); 
        
        // ถ้าข้อ 3 ล้มเหลว (สต็อกไม่พอ) -> Rollback ข้อ 1 และ 2 ทั้งหมด
    }
}
```

#### ตัวอย่างที่ 3: `readOnly = true` (ปรับปรุงประสิทธิภาพ)
```java
@Service
public class ReportServiceImpl {
    
    @Transactional(readOnly = true)  // ✅ บอก DB ว่าเป็นการอ่านอย่างเดียว Hibernate จะไม่ทำ Dirty Check
    public List<DailySalesDTO> getDailySalesReport(LocalDate date) {
        // ดึงข้อมูลมาวิเคราะห์เท่านั้น ไม่มีการแก้ไขข้อมูลใดๆ
        return reportRepository.findByDate(date);
    }
}
```

#### ตัวอย่างที่ 4: ระบุ `rollbackFor` ให้ชัดเจน
```java
@Service
public class PaymentServiceImpl {
    
    // ✅ ใช้ Exception.class เพื่อให้ Rollback ทุกชนิด (รวมถึง Custom Exception ของเรา)
    @Transactional(rollbackFor = {Exception.class, SystemGlobalException.class})
    public void recordPayment(PaymentRecordRequestDTO request) throws SystemGlobalException {
        // 1. บันทึก Payment
        paymentRepository.save(payment);
        
        // 2. อัปเดต Invoice
        invoiceService.updateStatus(request.getInvoiceId(), "PAID");
        
        // ถ้าเกิด SystemGlobalException (เช่น Invoice ไม่พบ) -> Rollback ทั้งหมด
    }
}
```

### 2.5 ข้อควรระวัง (Common Pitfalls)

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Self-Invocation (เรียกเมธอดภายในคลาสเดียวกัน)** | ถ้าเมธอด A (ไม่มี @Transactional) เรียกเมธอด B (มี @Transactional) ภายในคลาสเดียวกัน `@Transactional` จะไม่ทำงาน เพราะ Proxy ไม่ถูกเรียกใช้ | ย้ายเมธอด B ไปไว้คลาสอื่น หรือใช้ `@Autowired` เรียกตัวเอง (Self Injection) |
| **ใช้กับ `private` Method** | `@Transactional` ใช้กับ `private` ไม่ได้ เพราะ Proxy ทำงานผ่าน Interface/Public Method | เปลี่ยนเป็น `public` หรือ `protected` |
| **ลืม `rollbackFor = Exception.class`** | Default จะ Rollback เฉพาะ `RuntimeException` เท่านั้น ถ้าเกิด `IOException` หรือ Custom Exception จะไม่ Rollback | ระบุ `rollbackFor = Exception.class` หรือ `Throwable.class` |

**ตัวอย่าง Self-Invocation (ผิด)**
```java
@Service
public class BadService {
    public void methodA() {
        methodB(); // ❌ การเรียกแบบนี้ @Transactional บน methodB จะไม่ทำงาน!
    }
    
    @Transactional
    public void methodB() {
        // ทำงานกับ DB
    }
}
```
**วิธีแก้ไข (ถูก)**
```java
@Service
public class GoodService {
    @Autowired
    private GoodService self; // ✅ Inject ตัวเอง (Self Injection)
    
    public void methodA() {
        self.methodB(); // ✅ เรียกผ่าน Proxy ทำให้ Transaction ทำงาน
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void methodB() {
        // ทำงานกับ DB
    }
}
```

---

## 3. สรุปความแตกต่างและการใช้ร่วมกัน

| คุณสมบัติ | `@Override` | `@Transactional` |
|-----------|-------------|------------------|
| **สังกัด** | Java (java.lang) | Spring Framework (org.springframework.transaction.annotation) |
| **ประเภท** | Marker Annotation | Functional Annotation (มี Logic) |
| **จุดประสงค์** | ตรวจสอบความถูกต้องของการ Override | จัดการ Transaction ฐานข้อมูล |
| **ทำงานตอนไหน** | Compile Time (ตรวจสอบ Syntax) | Runtime (ใช้ AOP/Proxy) |
| **ใช้บ่อยที่ชั้นไหน** | ทุก Layer (เพื่อความปลอดภัยในการเขียนโค้ด) | Service Layer (Business Logic) |

### การใช้งานร่วมกัน (ใน Service Layer)
```java
@Service
public class JobServiceImpl extends GenericServiceImpl<TJob, JobRepository> {
    
    @Override  // ✅ แทนที่เมธอดของแม่
    @Transactional(rollbackFor = Exception.class)  // ✅ จัดการ Transaction
    public JobResponseDTO createJob(JobCreateRequestDTO request) {
        // Business Logic
    }
}
```

---

## 4. คำแนะนำในการใช้งานสำหรับโปรเจกต์ของเรา

1. **ใช้ `@Transactional` ที่ Service Layer เสมอ** (ไม่ใช่ Controller หรือ Repository)
2. **ระบุ `rollbackFor = Exception.class` เสมอ** เพื่อป้องกันไม่ให้ Custom Exception ของเราถูก Ignore
3. **ใช้ `readOnly = true` สำหรับเมธอดที่ `@GetMapping` หรือดึงข้อมูลอย่างเดียว** เพื่อเพิ่ม Performance
4. **หลีกเลี่ยงการเรียกเมธอด `@Transactional` ภายในคลาสเดียวกัน** (ใช้ Self Injection หรือแยกไปอีก Service)
5. **ใช้ `@Override` ทุกครั้งที่ Override เมธอด** เพื่อป้องกัน Bug สะกดผิด

---

# อธิบาย Annotation อื่นๆ ที่สำคัญใน Spring Framework แบบละเอียด

ต่อเนื่องจาก `@Override` และ `@Transactional` ที่ได้อธิบายไปแล้ว ในโปรเจกต์ Spring Boot ของเรา (ระบบบริหารจัดการอู่ซ่อมรถ) ยังมี Annotation สำคัญอีกหลายตัวที่ใช้ประจำในชีวิตประจำวัน ได้แก่ `@Service`, `@Repository`, `@Async`, และ `@EventListener`

เรามาทำความเข้าใจแต่ละตัวแบบเจาะลึกกันครับ

---

## 1. @Service

### 1.1 ความหมายและจุดประสงค์

**`@Service`** คือ Annotation ที่ใช้กำกับ **คลาสในชั้น Service Layer (Business Logic Layer)** เพื่อบอก Spring ว่า "คลาสนี้เป็นที่เก็บ Business Logic ของระบบ"

- เป็น **Specialization (ลูกหลาน)** ของ `@Component`
- Spring จะทำการ **Scan** และสร้าง Bean (Object) ของคลาสนี้ขึ้นมาใน Container โดยอัตโนมัติ (Dependency Injection)

### 1.2 ทำงานอย่างไร?

1. **Component Scanning**: Spring จะอ่าน Package ที่กำหนด (`@ComponentScan`) และพบคลาสที่มี `@Service`
2. **สร้าง Bean**: Spring สร้าง Instance เพียงหนึ่งเดียว (Singleton) และเก็บไว้ใน ApplicationContext
3. **Inject ได้**: เราสามารถใช้ `@Autowired` เพื่อนำ Bean นี้ไปใช้ที่อื่นได้ทันที

### 1.3 ต่างจาก `@Component` อย่างไร?

| Annotation | จุดประสงค์หลัก | ความแตกต่างทางเทคนิค |
|-----------|---------------|---------------------|
| `@Component` | ใช้กับคลาสทั่วไปที่ไม่เข้าพวกกับ Layer อื่น | เป็น Generic (ทั่วไป) |
| `@Service` | ใช้กับ Business Logic Layer | **ไม่มี** ข้อแตกต่างทางเทคนิคกับ `@Component` (มันคือ `@Component` อีกรูปแบบหนึ่ง) แต่ช่วยให้ **คนอ่านโค้ดรู้ทันที** ว่าคลาสนี้มีหน้าที่ทำ Business Logic |
| `@Repository` | ใช้กับ Data Access Layer | มีการเพิ่ม **PersistenceExceptionTranslation** (อธิบายในหัวข้อถัดไป) |
| `@Controller` | ใช้กับ Web/Presentation Layer | ทำงานร่วมกับ Web Dispatcher |

> สรุป: `@Service` คือการ "เซ็นชื่อ" ให้โค้ดอ่านง่ายขึ้น และเป็นมาตรฐานของวงการ (Best Practice)

### 1.4 ตัวอย่างในระบบของเรา

```java
package com.template.app.modules.job.application.impl;

import org.springframework.stereotype.Service;

@Service  // ✅ บอก Spring ว่าคลาสนี้เป็น Service Bean
public class JobServiceImpl extends GenericServiceImpl<TJob, JobRepository> 
        implements JobService {

    // Business Logic ทั้งหมดเกี่ยวกับ Job Card อยู่ที่นี่
    @Override
    public JobResponseDTO createJob(JobCreateRequestDTO request) {
        // Logic การสร้างใบงาน
        return new JobResponseDTO();
    }
}
```

### 1.5 ข้อควรระวัง
- **อย่า** ใช้ `@Service` กับ Repository หรือ Controller (จะทำให้สับสน)
- **อย่า** มี State (ตัวแปรที่เปลี่ยนแปลงได้) ภายใน `@Service` เพราะมันเป็น Singleton ถ้ามี State และมีการเปลี่ยนแปลงพร้อมกันหลาย Request จะเกิด Race Condition

---

## 2. @Repository

### 2.1 ความหมายและจุดประสงค์

**`@Repository`** คือ Annotation ที่ใช้กำกับ **คลาสในชั้น Data Access Layer (DAO / Repository)** เพื่อบอก Spring ว่า "คลาสนี้ทำงานกับฐานข้อมูล"

- เป็น Specialization ของ `@Component` เช่นกัน
- **มีฟังก์ชันพิเศษ**: Spring จะเพิ่ม **PersistenceExceptionTranslationPostProcessor** ซึ่งจะแปลง Exception ที่เกิดจาก JPA/Hibernate (เช่น `PersistenceException`, `SQLException`) ให้เป็น **Unchecked Exception** ของ Spring ที่ชื่อ `DataAccessException`

### 2.2 PersistenceExceptionTranslation (ข้อดีพิเศษ)

| ปัญหา | ไม่ใช้ @Repository | ใช้ @Repository |
|-------|-------------------|-----------------|
| เกิด `SQLException` | ต้อง `try-catch` และจัดการเอง (`throws SQLException`) | Spring จับและแปลงเป็น `DataAccessException` อัตโนมัติ |
| เกิด `PersistenceException` | ต้องรู้รายละเอียดของ Hibernate/JPA | แปลงเป็น `DataAccessException` ที่มีความหมายทั่วไป |
| การเขียนโค้ด | เต็มไปด้วย Try-Catch รก | เขียนโค้ดสะอาด ปล่อยให้ GlobalExceptionHandler จัดการ |

### 2.3 ทำงานอย่างไร?

```java
// กรณีที่ไม่มี @Repository (ต้องจัดการ Exception เอง)
public class BadUserRepository {
    public User findById(Long id) throws SQLException { // ❌ ต้อง throws
        // query DB
    }
}

// กรณีที่มี @Repository (Spring จัดการให้)
@Repository  // ✅ Spring จะแปลง SQLException เป็น DataAccessException
public class UserRepositoryImpl implements UserRepository {
    public User findById(Long id) { // ✅ ไม่ต้อง throws SQLException
        // query DB
    }
}
```

### 2.4 ตัวอย่างในระบบของเรา

```java
package com.template.app.modules.job.infrastructure.repository.impl;

import org.springframework.stereotype.Repository;

@Repository  // ✅ บอก Spring ว่าคลาสนี้ติดต่อฐานข้อมูล และจัดการ Exception ให้
public class JobRepositoryImpl extends GenericBusinessRepositoryImpl<TJob, JobEntity> 
        implements JobRepository {

    public JobRepositoryImpl(JobMapper mapper, 
                             SimpleJpaRepository<JobEntity, UUID> jpaRepository) {
        super(mapper, jpaRepository, JobEntity.class);
    }
    
    // ไม่ต้องเขียน try-catch SQLException เยอะๆ Spring จะแปลงให้เอง
    @Override
    public Optional<TJob> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toEntity); // แปลง Entity เป็น Domain
    }
}
```

### 2.5 ความแตกต่างระหว่าง `@Repository` กับ `@Service`

| คุณสมบัติ | `@Repository` | `@Service` |
|-----------|---------------|------------|
| **Layer** | Data Access (ติดต่อ DB) | Business Logic |
| **Exception** | แปลง SQL/JPA Exception เป็น `DataAccessException` | ไม่มีการจัดการ Exception พิเศษ (แต่ใช้ `@Transactional` จัดการ Rollback) |
| **การใช้** | ใช้กับ Interface หรือ Class ที่มี Method `save()`, `findById()` | ใช้กับ Class ที่มี Logic เชิงธุรกิจ (คำนวณราคา, เปลี่ยนสถานะ) |

---

## 3. @Async

### 3.1 ความหมายและจุดประสงค์

**`@Async`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้เมธอดนี้ทำงานใน Thread อื่น (แบบ Asynchronous) อย่ารอให้มันเสร็จ"**

- ใช้กับเมธอดใน Service Layer
- ต้องเปิดใช้งานด้วย `@EnableAsync` บน Configuration Class (หรือ Main Application)

### 3.2 ทำงานอย่างไร? (Behind the Scenes)

1. **Proxy ถูกสร้าง**: Spring สร้าง Proxy Object ของคลาสที่ถูกเรียก
2. **ส่งงานเข้า Queue**: เมื่อเรียกเมธอด `@Async` Proxy จะส่งงานนี้ไปยัง **TaskExecutor** (Thread Pool)
3. **คืนค่าทันที**: เมธอดจะคืนค่า `void` หรือ `CompletableFuture<T>` ทันที (ไม่รอให้ทำงานเสร็จ)
4. **ทำงานเบื้องหลัง**: Thread Pool จะทำงานจริงๆ แบบ Async

### 3.3 พารามิเตอร์ที่สำคัญ

| พารามิเตอร์ | คำอธิบาย |
|-----------|----------|
| **`value`** | ระบุชื่อ Thread Pool ที่ต้องการใช้ (ถ้ามีหลาย Pool) |

### 3.4 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: การบันทึก Log (ไม่ต้องการรอ)
```java
package com.template.app.modules.logging;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Async  // ✅ บันทึก Log แบบ Async ไม่อยากรอให้เขียน Log เสร็จก่อนตอบกลับ API
    public void saveErrorLogAsync(ErrorLogSchema errorLog) {
        // ทำงานที่ใช้เวลาพอสมควร (เขียนลง MongoDB)
        errorLogRepository.save(errorLog);
        // เมื่อ Method นี้ทำงานเสร็จ ผู้ใช้ที่เรียก API จะไม่ต้องรอ
    }
}
```

#### ตัวอย่างที่ 2: การส่งอีเมล (ใช้เวลานาน)
```java
package com.template.app.modules.email.application.impl;

@Service
public class EmailServiceImpl {

    @Async("emailTaskExecutor")  // ✅ ระบุ Thread Pool ที่ชื่อ emailTaskExecutor
    public CompletableFuture<Boolean> sendEmailAsync(String to, String subject, String body) {
        // ส่งอีเมล (อาจใช้เวลา 2-3 วินาที)
        boolean success = smtpClient.send(to, subject, body);
        return CompletableFuture.completedFuture(success);
    }
}
```

#### ตัวอย่างที่ 3: การสร้าง PDF / Report (งานหนัก)
```java
@Service
public class ReportServiceImpl {

    @Async
    public void generateHeavyReportAsync(UUID reportId) {
        // งานที่ใช้เวลานาน (คำนวณข้อมูล, สร้าง PDF ขนาดใหญ่)
        ReportData data = fetchLargeData();
        byte[] pdf = reportGenerator.generate(data);
        storageService.saveToS3(pdf, reportId);
        
        // อัปเดตสถานะเป็น "เสร็จ" (ไม่ต้องรอ User)
        reportRepository.updateStatus(reportId, "COMPLETED");
    }
}
```

### 3.5 การกำหนดค่า Thread Pool (สำหรับ Production)
```java
package com.template.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync  // ✅ เปิดใช้งาน Async
public class AsyncConfig {

    @Bean(name = "emailTaskExecutor")
    public Executor emailTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);        // จำนวน Thread ที่พร้อมทำงานเสมอ
        executor.setMaxPoolSize(10);        // จำนวน Thread สูงสุด
        executor.setQueueCapacity(100);     // จำนวนงานที่รอในคิว
        executor.setThreadNamePrefix("Email-Thread-");
        executor.initialize();
        return executor;
    }
}
```

### 3.6 ข้อควรระวัง (Common Pitfalls)

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Self-Invocation** | การเรียกเมธอด `@Async` ภายในคลาสเดียวกันจะไม่ทำงาน (Proxy ไม่ถูกเรียก) | Inject ตัวเอง (Self Injection) หรือแยกไปคลาสอื่น |
| **ใช้กับ `private`** | `@Async` ใช้กับ `private` ไม่ได้ (Proxy ทำงานผ่าน Public) | เปลี่ยนเป็น `public` |
| **Exception Handling** | ถ้าเกิด Exception ในเมธอด `@Async` ที่คืนค่า `void` จะไม่ถูกส่งไปที่ Caller | ใช้ `@Async` คู่กับ `CompletableFuture<T>` เพื่อรับ Exception |
| **Context Propagation** | `@Async` จะไม่ส่งต่อ `SecurityContext` และ `MDC` (Logging) อัตโนมัติ | ต้องใช้ `DelegatingSecurityContextAsyncTaskExecutor` หรือตั้งค่า MDC ใหม่ใน Thread |

**ตัวอย่างข้อผิดพลาด Self-Invocation (ผิด)**
```java
@Service
public class BadAsyncService {
    public void methodA() {
        methodB(); // ❌ @Async บน methodB จะไม่ทำงาน (เรียกตรงๆ ไม่ผ่าน Proxy)
    }
    @Async
    public void methodB() { ... }
}
```
**วิธีแก้ไข (ถูก)**
```java
@Service
public class GoodAsyncService {
    @Autowired
    private GoodAsyncService self; // ✅ Self Injection
    
    public void methodA() {
        self.methodB(); // ✅ เรียกผ่าน Proxy ทำให้ Async ทำงาน
    }
    @Async
    public void methodB() { ... }
}
```

---

## 4. @EventListener

### 4.1 ความหมายและจุดประสงค์

**`@EventListener`** คือ Annotation ที่ใช้บอก Spring ว่า **"เมธอดนี้จะทำงานเมื่อเกิด Event (เหตุการณ์) ที่ระบุขึ้นในระบบ"**

- ใช้ใน Service หรือ Component Layer
- เป็นส่วนหนึ่งของ **Event-Driven Architecture** (การทำงานแบบขับเคลื่อนด้วยเหตุการณ์)
- ช่วย **ลดการ耦合 (Decoupling)** ระหว่างโมดูล

### 4.2 ทำงานอย่างไร?

1. **มี Event Class**: สร้างคลาสที่ extends `ApplicationEvent` (หรือเป็น普通的 POJO)
2. **มี Publisher**: ใช้ `ApplicationEventPublisher` (หรือ `@Autowired`) เพื่อ `publishEvent(event)`
3. **มี Listener**: เมธอดที่มี `@EventListener` จะถูกเรียกอัตโนมัติเมื่อ Event ถูก Publish

### 4.3 ทำงานแบบ Synchronous หรือ Asynchronous?
- **ค่าเริ่มต้น**: ทำงานแบบ **Synchronous** (ทำงานใน Thread เดียวกับ Publisher) ถ้างานใช้เวลานาน จะทำให้ Publisher ช้าตาม
- **ใช้ร่วมกับ `@Async`**: ถ้าใส่ `@Async` เพิ่ม จะทำงานแบบ **Asynchronous** (คนละ Thread)

### 4.4 ตัวอย่างในระบบของเรา

#### 4.4.1 สร้าง Event Class
```java
package com.template.app.modules.quotation.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class QuotationApprovedEvent extends ApplicationEvent {
    private final UUID quotationId;
    private final UUID jobId;
    private final UUID customerId;
    private final String customerEmail;

    public QuotationApprovedEvent(Object source, UUID quotationId, UUID jobId, 
                                  UUID customerId, String customerEmail) {
        super(source);
        this.quotationId = quotationId;
        this.jobId = jobId;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
    }
}
```

#### 4.4.2 Publisher (เมื่อ Quotation ถูกอนุมัติ)
```java
package com.template.app.modules.quotation.application.impl;

import org.springframework.context.ApplicationEventPublisher;

@Service
public class QuotationServiceImpl {

    @Autowired
    private ApplicationEventPublisher eventPublisher; // ✅ ตัวกลางในการ Publish Event

    @Transactional
    public void approveQuotation(UUID quotationId) {
        // 1. อัปเดตสถานะ Quotation
        quotationRepository.updateStatus(quotationId, "APPROVED");
        
        // 2. สร้าง Event และ Publish (บอกต่อว่า Quotation ถูกอนุมัติแล้ว)
        QuotationApprovedEvent event = new QuotationApprovedEvent(
            this, 
            quotationId, 
            jobId, 
            customerId, 
            customerEmail
        );
        eventPublisher.publishEvent(event);  // ✅ ปล่อย Event
        
        // 3. ทำงานอื่นๆ ต่อไป (ไม่ต้องรอ Listener)
        // ใครที่สนใจ Event นี้จะไปทำงานต่อเอง
    }
}
```

#### 4.4.3 Listener 1: ส่งอีเมลให้ลูกค้า (ทำงาน Async)
```java
package com.template.app.modules.quotation.application.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class QuotationEmailListener {

    private final EmailService emailService;

    @Async  // ✅ ทำงานแบบ Async (ไม่ให้ช้าตัวหลัก)
    @EventListener  // ✅ ฟัง Event: QuotationApprovedEvent
    public void handleQuotationApproved(QuotationApprovedEvent event) {
        // ส่งอีเมลแจ้งลูกค้าว่า Quotation อนุมัติแล้ว พร้อมแนบ PDF
        emailService.sendQuotationApprovedEmail(
            event.getCustomerEmail(),
            event.getQuotationId()
        );
        System.out.println("Email sent to customer: " + event.getCustomerEmail());
    }
}
```

#### 4.4.4 Listener 2: สร้าง Purchase Order อัตโนมัติ (ทำงาน Sync)
```java
package com.template.app.modules.purchase.application.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderListener {

    private final PurchaseOrderService purchaseOrderService;

    @EventListener  // ✅ ทำงานแบบ Sync (ต้องรอให้ PO สร้างเสร็จถึงจะไปต่อ)
    public void handleQuotationApproved(QuotationApprovedEvent event) {
        // เมื่อ Quotation อนุมัติ ให้สร้าง PO อัตโนมัติ (ถ้าต้องการ)
        purchaseOrderService.createFromQuotation(event.getQuotationId());
    }
}
```

#### 4.4.5 Listener 3: สร้าง Notification ในระบบ
```java
@Component
public class NotificationListener {

    private final NotificationService notificationService;

    @EventListener
    public void handleQuotationApproved(QuotationApprovedEvent event) {
        // สร้าง Notification ให้พนักงานหน้าร้านเห็น
        notificationService.createNotification(
            event.getJobId(),
            "Quotation #" + event.getQuotationId() + " has been approved."
        );
    }
}
```

### 4.5 @EventListener แบบมีเงื่อนไข (Conditional)
ใช้ `condition` เพื่อให้ทำงานเฉพาะบางกรณี:

```java
@Component
public class ConditionalListener {

    // ✅ ทำงานเฉพาะ Event ที่ amount > 10000 เท่านั้น
    @EventListener(condition = "#event.amount > 10000")
    public void handleLargeAmountEvent(QuotationApprovedEvent event) {
        // ส่งแจ้งเตือนผู้จัดการ (สำหรับออเดอร์มูลค่าสูง)
        notificationService.notifyManager(event.getQuotationId());
    }
}
```

### 4.6 ประโยชน์ของ Event-Driven

| ประโยชน์ | คำอธิบาย |
|---------|----------|
| **ลดการ  (Decoupling)** | QuotationService ไม่ต้องรู้จัก EmailService, PurchaseOrderService โดยตรง (แค่ Publish Event) |
| **เพิ่มความยืดหยุ่น (Extensible)** | ถ้าอยากเพิ่มฟังก์ชันใหม่ (เช่น ส่ง LINE Notify) แค่เพิ่ม Listener โดยไม่ต้องแก้ไข QuotationService |
| **จัดการงาน Async ง่าย** | ใช้ `@Async` ร่วมกับ `@EventListener` เพื่อทำงานหนักแบบไม่บล็อก |
| **อ่านง่าย (Readability)** | การไหลของโปรแกรมชัดเจน: "เมื่อ X เกิดขึ้น ให้ Y และ Z ทำงาน" |

---

## 5. สรุปตารางเปรียบเทียบ Annotation ทั้ง 4 ตัว

| Annotation | หมวดหมู่ | ใช้ที่ Layer | หน้าที่หลัก | ทำงานแบบ |
|-----------|---------|-------------|-----------|----------|
| **`@Service`** | Stereotype (โครงสร้าง) | Service (Business Logic) | บอก Spring ว่าคลาสนี้เป็น Service Bean | Compile-Time (Component Scan) |
| **`@Repository`** | Stereotype (โครงสร้าง) | Repository (Data Access) | บอก Spring ว่าคลาสนี้เป็น DAO และแปลง Exception ของ DB | Compile-Time + Runtime (Exception Translation) |
| **`@Async`** | Behavioral (พฤติกรรม) | Service (Method) | ทำให้เมธอดทำงานแบบ Asynchronous (ไม่รอ) | Runtime (Proxy + Thread Pool) |
| **`@EventListener`** | Event-Driven (เหตุการณ์) | Any Component | รอฟังและตอบสนองต่อ Event ที่เกิดขึ้น | Runtime (ApplicationEventMulticaster) |

---

## 6. คำแนะนำสำหรับโปรเจกต์ Auto Repair Shop

1. **`@Service`**: ใช้กับ `*ServiceImpl.java` ทุกคลาส (Job, Quotation, Payment, etc.)
2. **`@Repository`**: ใช้กับ `*RepositoryImpl.java` ทุกคลาส
3. **`@Async`**: ใช้กับงานที่ใช้เวลานาน เช่น:
   - การส่งอีเมล (`EmailService.sendEmail`)
   - การสร้าง PDF ขนาดใหญ่ (`ReportGenerator.generate`)
   - การบันทึก Log (`LogService.saveErrorLogAsync`)
   - การเรียก API ภายนอก (LINE Notify, SMS Gateway)
4. **`@EventListener`**: ใช้สำหรับแยก Logic ที่ไม่เกี่ยวข้องกันออกจากกัน เช่น:
   - Quotation Approved → ส่งอีเมล, สร้าง PO, แจ้งเตือนพนักงาน
   - Job Closed → อัปเดต Dashboard, สรุปยอดขาย
   - Payment Received → สร้าง Receipt, ส่งอีเมลยืนยัน

---

## 7. ตัวอย่างการทำงานร่วมกันแบบครบวงจร

```java
// 1. QuotationService (Publisher)
@Service
public class QuotationServiceImpl {
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @Transactional
    public void approveQuotation(UUID id) {
        // ... approve logic ...
        eventPublisher.publishEvent(new QuotationApprovedEvent(this, id, ...));
    }
}

// 2. EmailListener (Async)
@Component
public class EmailListener {
    @Async @EventListener
    public void onQuotationApproved(QuotationApprovedEvent event) {
        emailService.sendEmail(...); // ไม่รอ
    }
}

// 3. PurchaseListener (Sync)
@Component
public class PurchaseListener {
    @EventListener
    public void onQuotationApproved(QuotationApprovedEvent event) {
        purchaseService.createPO(...); // รอให้เสร็จ
    }
}
```

เมื่อ `approveQuotation()` ถูกเรียก:
1. Transaction เริ่มต้น
2. อัปเดต Quotation
3. Publish Event
4. **PurchaseListener** ทำงาน (Sync, ยังอยู่ใน Transaction เดิม)
5. Transaction Commit
6. **EmailListener** ทำงาน (Async, หลังจาก Commit แล้ว)

---

# อธิบาย Annotation อื่นๆ ที่สำคัญใน Spring Framework (ต่อ)

หลังจากที่เราได้เรียนรู้ `@Override`, `@Transactional`, `@Service`, `@Repository`, `@Async`, และ `@EventListener` ไปแล้ว ในโปรเจกต์ Spring Boot ของเรา (ระบบบริหารจัดการอู่ซ่อมรถ) ยังมี Annotation ที่เกี่ยวข้องกับ **การกำหนด Bean** และ **การแก้ปัญหาการ Inject** อีกหลายตัวที่ขาดไม่ได้เลย ได้แก่:

- `@Component` (รากฐานของ Bean ทั้งหมด)
- `@Configuration` และ `@Bean` (การกำหนดค่าด้วย Java Config)
- `@Qualifier` และ `@Primary` (การเลือก Bean ที่ต้องการ Inject)

เรามาทำความเข้าใจแบบเจาะลึกและนำไปใช้ในระบบของเราจริงกันครับ

---

## 1. @Component (รากฐานของ Dependency Injection)

### 1.1 ความหมายและจุดประสงค์

**`@Component`** คือ **Annotation ระดับคลาส (Class-Level)** ที่บอก Spring ว่า **"คลาสนี้คือ Bean (Object ที่ Spring จะจัดการให้)"**

- Spring จะทำ **Component Scanning** (ค้นหาคลาสที่มี `@Component` และลูกหลานของมัน) แล้วสร้าง Instance ขึ้นมาเก็บไว้ใน **ApplicationContext** (IoC Container)
- เมื่อเราต้องการใช้งาน เราสามารถใช้ `@Autowired` เพื่อให้ Spring Inject ให้เราได้เลย

### 1.2 ความสัมพันธ์กับ `@Service`, `@Repository`, `@Controller`

| Annotation | ความหมายในทางเทคนิค |
|-----------|---------------------|
| `@Component` | แม่ (Parent) ของทุก Stereotype Annotation |
| `@Service` | **`@Component`** + ระบุว่าเป็น Business Logic Layer |
| `@Repository` | **`@Component`** + ระบุว่าเป็น Data Access Layer (และแปลง Exception ของ DB) |
| `@Controller` | **`@Component`** + ระบุว่าเป็น Web Controller |
| `@RestController` | **`@Controller`** + `@ResponseBody` (ใช้กับ REST API) |

> **สรุป**: `@Service`, `@Repository`, `@Controller` คือ "ลูก" ของ `@Component` ทั้งหมด ลูกเหล่านี้มีฟังก์ชันเสริมเพิ่มเข้าไป (เช่น `@Repository` แปลง Exception) แต่ถ้าเป็นคลาสทั่วไปที่ไม่เข้าพวกกับ Layer ไหน ให้ใช้ `@Component` เลย

### 1.3 ตัวอย่างในระบบของเรา (ใช้กับ Utility หรือ Helper)

```java
package com.template.app.utils;

import org.springframework.stereotype.Component;

@Component  // ✅ บอก Spring ว่าให้สร้าง Bean ของคลาสนี้ (Singleton)
public class ExcelGenerator {

    // เมธอดช่วยสร้างไฟล์ Excel จาก Data List
    public byte[] generateExcel(List<?> data, String sheetName) {
        // ... Logic การสร้าง Excel ด้วย Apache POI
        return new byte[0];
    }
    
    // เมธอดช่วยสร้าง CSV
    public String generateCsv(List<?> data) {
        // ... Logic การสร้าง CSV
        return "";
    }
}
```

เมื่อเราใช้ `@Component` แล้ว เราสามารถ `@Autowired` มาใช้ใน Service อื่นๆ ได้ทันที:

```java
@Service
public class ReportServiceImpl {

    @Autowired
    private ExcelGenerator excelGenerator;  // ✅ Spring Inject ให้อัตโนมัติ

    public byte[] exportExcel() {
        List<DailySalesDTO> data = getData();
        return excelGenerator.generateExcel(data, "Daily Sales");
    }
}
```

---

## 2. @Configuration และ @Bean (Java-based Configuration)

### 2.1 ความหมายและจุดประสงค์

**`@Configuration`** และ **`@Bean`** คือ Annotation ที่ใช้แทน **ไฟล์ XML Configuration** (แบบเก่า) โดยเราจะกำหนด Bean ผ่านโค้ด Java

| Annotation | ใช้ที่ไหน | หน้าที่ |
|-----------|---------|---------|
| **`@Configuration`** | ระดับคลาส (Class-Level) | บอก Spring ว่าคลาสนี้เป็น **แหล่งรวม Bean Definitions** (เหมือนไฟล์ `applicationContext.xml`) |
| **`@Bean`** | ระดับเมธอด (Method-Level) | บอก Spring ว่า "เมธอดนี้จะสร้างและคืนค่า Object ซึ่งจะกลายเป็น Bean ใน Container" |

### 2.2 ทำงานอย่างไร?

1. Spring อ่านคลาสที่ `@Configuration`
2. Spring เรียกเมธอดที่มี `@Bean` ทุกตัว
3. Object ที่คืนจากเมธอดจะถูกเก็บเป็น Bean ใน ApplicationContext

> Spring จะใช้ **CGLIB Proxy** กับคลาส `@Configuration` เพื่อให้แน่ใจว่าเมธอด `@Bean` ถูกเรียกเพียงครั้งเดียว (Singleton) ต่อการเรียกหลายครั้ง

### 2.3 ใช้เมื่อไหร่? (เมื่อไหร่ควรใช้ @Bean แทน @Component)

| กรณี | ใช้ `@Component` | ใช้ `@Bean` ใน `@Configuration` |
|------|-----------------|--------------------------------|
| **คลาสของเราเอง** | ✅ (ใส่ `@Component` หรือ `@Service` เลย) | ❌ ไม่จำเป็น |
| **คลาสจาก Library ภายนอก** | ❌ (เราแก้โค้ดไม่ได้) | ✅ (เราต้องบอก Spring ว่าสร้างอย่างไร) |
| **ต้องการสร้าง Bean แบบมีพารามิเตอร์** | ❌ (Constructor Injection ใช้ได้ แต่ถ้าไม่ใช่ของเรา) | ✅ (ใช้ `new ThirdPartyClass(param1, param2)`) |
| **ต้องการกำหนด Configuration เพิ่มเติม** | ❌ | ✅ (เช่น ตั้งค่าการเชื่อมต่อ DB, ThreadPool) |

### 2.4 ตัวอย่างในระบบของเรา (ใช้จริง)

#### ตัวอย่างที่ 1: Config ThreadPool สำหรับ Async
```java
package com.template.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration  // ✅ บอก Spring ว่าคลาสนี้มี Bean Definitions
@EnableAsync    // ✅ เปิดใช้งาน Async (จะไปเปิดในไฟล์นี้ก็ได้)
public class AsyncConfig {

    @Bean(name = "emailTaskExecutor")  // ✅ เมธอดนี้จะสร้าง Bean ชื่อ emailTaskExecutor
    public Executor emailTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Email-Thread-");
        executor.initialize();
        return executor;  // ✅ Spring จะนำ Object นี้ไปเก็บเป็น Bean
    }

    @Bean(name = "pdfTaskExecutor")  // ✅ สร้างอีก Bean สำหรับงาน PDF
    public Executor pdfTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("PDF-Thread-");
        executor.initialize();
        return executor;
    }
}
```

#### ตัวอย่างที่ 2: Config RestTemplate (สำหรับเรียก API ภายนอก)
```java
package com.template.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean  // ✅ สร้าง RestTemplate Bean ไว้ใช้เรียก API ภายนอก (เช่น LINE Notify, Supplier API)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

#### ตัวอย่างที่ 3: Config Kafka (Producer/Consumer)
```java
package com.template.app.configuration.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean  // ✅ สร้าง KafkaTemplate Bean ไว้ส่งข้อความ
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
```

### 2.5 ความแตกต่างระหว่าง `@Component` กับ `@Bean` (สรุป)

| คุณสมบัติ | `@Component` | `@Bean` |
|-----------|--------------|---------|
| **อยู่ที่ไหน** | ใส่ที่ Class (Type-Level) | ใส่ที่ Method (Method-Level) |
| **ควบคุมได้** | ต้องแก้ไขโค้ดคลาสนั้น (เพื่อใส่ Annotation) | ควบคุมใน Config Class ได้เลย ไม่ต้องแก้คลาสต้นทาง |
| **เหมาะกับ** | คลาสของเราเองที่เขียนขึ้น | คลาสจาก Library ภายนอก (Third-Party) |
| **Singleton** | Default (Singleton) | Default (Singleton) ถ้าอยู่ใน `@Configuration` |
| **การทำงาน** | Component Scan | เรียกเมธอดโดยตรง |

---

## 3. @Qualifier และ @Primary (การเลือก Bean ที่ต้องการ)

### 3.1 ปัญหาที่เกิดขึ้น (เมื่อมี Bean หลายตัวประเภทเดียวกัน)

ในระบบของเรา เราอาจมี Bean ที่ประเภทเดียวกันมากกว่า 1 ตัว เช่น:

- `RestTemplate` 2 ตัว: ตัวหนึ่งเรียก API ภายใน, อีกตัวเรียก API ภายนอก (มี timeout ต่างกัน)
- `ObjectMapper` 2 ตัว: ตัวหนึ่งใช้ Default, อีกตัวใช้ Custom Date Format
- `TaskExecutor` 2 ตัว: อันหนึ่งใช้ส่ง Email, อีกอันใช้สร้าง PDF

ถ้าเราพิมพ์ `@Autowired` โดยไม่ระบุเพิ่มเติม **Spring จะไม่รู้ว่าจะ Inject ตัวไหน** แล้วจะเกิด Error ทันที (`NoUniqueBeanDefinitionException`)

### 3.2 @Qualifier (ระบุชื่อเฉพาะ)

**`@Qualifier`** คือ Annotation ที่ใช้ **ระบุชื่อ (หรือ ID) ของ Bean ที่ต้องการ Inject** อย่างชัดเจน

#### ตัวอย่าง: มี RestTemplate 2 ตัว
```java
package com.template.app.configuration;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "internalRestTemplate")  // ✅ ตั้งชื่อ Bean ว่า "internalRestTemplate"
    public RestTemplate internalRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Bean(name = "externalRestTemplate")  // ✅ ตั้งชื่อ Bean ว่า "externalRestTemplate"
    public RestTemplate externalRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(60))
                .build();
    }
}
```

#### เวลา Inject ใน Service (ใช้ @Qualifier)
```java
package com.template.app.modules.purchase.application.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SupplierApiService {

    // ✅ Inject ตัวที่ชื่อ "externalRestTemplate" (Timeout สูง)
    @Autowired
    @Qualifier("externalRestTemplate")
    private RestTemplate externalRestTemplate;

    public String callSupplierApi(String url) {
        // ใช้ RestTemplate นี้เรียก Supplier (ใช้เวลาโหลดนาน)
        return externalRestTemplate.getForObject(url, String.class);
    }
}

@Service
public class InternalReportService {

    // ✅ Inject ตัวที่ชื่อ "internalRestTemplate" (Timeout ต่ำ)
    @Autowired
    @Qualifier("internalRestTemplate")
    private RestTemplate internalRestTemplate;

    public String callInternalApi(String url) {
        // ใช้ RestTemplate นี้เรียก API ภายใน (เร็ว)
        return internalRestTemplate.getForObject(url, String.class);
    }
}
```

#### ตัวอย่างที่ 2: ObjectMapper แบบ Custom
```java
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper defaultObjectMapper() {
        return new ObjectMapper(); // Default
    }

    @Bean
    @Qualifier("dateFormatMapper")  // ✅ ตั้งชื่อเฉพาะ
    public ObjectMapper dateFormatObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
        return mapper;
    }
}

@Service
public class PaymentService {
    
    @Autowired
    @Qualifier("dateFormatMapper")  // ✅ ใช้ตัวที่ format วันที่
    private ObjectMapper objectMapper;
    
    public String formatPaymentDate(TPayment payment) {
        return objectMapper.writeValueAsString(payment);
    }
}
```

### 3.3 @Primary (ตั้งค่าเริ่มต้น / Default)

**`@Primary`** คือ Annotation ที่ใช้บอก Spring ว่า **"ถ้าไม่ระบุ @Qualifier ให้เลือก Bean ตัวนี้เป็นค่าเริ่มต้น"**

- ใช้ได้กับ `@Component`, `@Service`, `@Bean`
- ช่วยลดความยุ่งยากในการเขียน `@Qualifier` ทุกครั้ง

#### ตัวอย่าง: กำหนด Default RestTemplate
```java
@Configuration
public class RestTemplateConfig {

    @Bean
    @Primary  // ✅ ถ้าไม่ระบุ Qualifier ให้ใช้ตัวนี้
    public RestTemplate defaultRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Bean(name = "externalRestTemplate")
    public RestTemplate externalRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(60))
                .build();
    }
}

// ใน Service ที่ไม่สนใจละเอียด ใช้ Default
@Service
public class SimpleReportService {
    @Autowired
    private RestTemplate restTemplate;  // ✅ จะได้ตัวที่ @Primary (defaultRestTemplate)
}
```

### 3.4 สรุปความแตกต่างระหว่าง @Qualifier และ @Primary

| คุณสมบัติ | `@Qualifier` | `@Primary` |
|-----------|--------------|------------|
| **จุดประสงค์** | ระบุ **ชื่อเฉพาะ** ของ Bean ที่ต้องการ | กำหนด **ค่าเริ่มต้น (Default)** |
| **ใช้ที่ไหน** | กับ `@Autowired` หรือ `@Inject` (ฝั่งผู้ใช้) | กับ `@Bean` หรือ `@Component` (ฝั่งผู้ให้) |
| **ถ้าเจอหลายตัว** | ใช้ชื่อที่ระบุ | ใช้ตัวที่มี `@Primary` |
| **มีได้กี่ตัว** | มีได้หลายชื่อ | มีได้ **เพียง 1 ตัว** ต่อประเภท (ถ้ามี 2 ตัว Spring จะสับสน) |

---

## 4. การทำงานร่วมกันแบบครบวงจรในโปรเจกต์ของเรา

### 4.1 Scenario: มี 2 KafkaTemplate (สำหรับ Topic ต่างกัน)

```java
// 1. Config
@Configuration
public class KafkaConfig {
    
    @Bean(name = "orderKafkaTemplate")
    public KafkaTemplate<String, String> orderKafkaTemplate() {
        // สร้าง KafkaTemplate สำหรับส่ง Order Event
        return new KafkaTemplate<>(orderProducerFactory());
    }
    
    @Bean(name = "notificationKafkaTemplate")
    public KafkaTemplate<String, String> notificationKafkaTemplate() {
        // สร้าง KafkaTemplate สำหรับส่ง Notification Event
        return new KafkaTemplate<>(notificationProducerFactory());
    }
}

// 2. ใช้ใน Service
@Service
public class OrderEventPublisher {
    
    @Autowired
    @Qualifier("orderKafkaTemplate")  // ✅ ใช้เฉพาะ Order Template
    private KafkaTemplate<String, String> orderKafkaTemplate;
    
    public void publishOrderCreated(UUID orderId) {
        orderKafkaTemplate.send("order-topic", orderId.toString());
    }
}
```

### 4.2 Scenario: Database Connection (Multi-Tenant) โดยใช้ @Qualifier

สมมติว่าระบบเราต้องเชื่อมต่อกับ 2 ฐานข้อมูล (Master / Reporting) เราจะมี 2 `DataSource`:

```java
@Configuration
public class DatabaseConfig {
    
    @Bean(name = "masterDataSource")
    @Primary  // ✅ Default
    public DataSource masterDataSource() {
        // config
    }
    
    @Bean(name = "reportingDataSource")
    public DataSource reportingDataSource() {
        // config
    }
    
    @Bean(name = "masterEntityManager")
    public LocalContainerEntityManagerFactoryBean masterEntityManager(
            @Qualifier("masterDataSource") DataSource dataSource) { // ✅ ใช้ Qualifier เลือก
        // ...
    }
}
```

---

## 5. สรุปตารางเปรียบเทียบ Annotation ทั้ง 5 ตัว

| Annotation | ใช้กับ | หน้าที่หลัก | ความสัมพันธ์กับอื่นๆ |
|-----------|-------|------------|---------------------|
| **`@Component`** | Class | สร้าง Bean อัตโนมัติ (Auto-Detect) | รากฐานของ `@Service`, `@Repository`, `@Controller` |
| **`@Configuration`** | Class | บอกว่าเป็นแหล่งรวม Bean Definitions | ใช้คู่กับ `@Bean` |
| **`@Bean`** | Method | สร้าง Bean จาก Method (ใช้กับ Library ภายนอก) | ต้องอยู่ภายใน `@Configuration` (หรือ `@Component`) |
| **`@Qualifier`** | Field, Parameter, Method | ระบุชื่อ Bean ที่ต้องการ Inject (ตอนใช้งาน) | ใช้คู่กับ `@Autowired` |
| **`@Primary`** | Class or Method | กำหนด Bean เริ่มต้น (Default) | ใช้กับ `@Bean` หรือ `@Component` โดยตรง |

---

## 6. คำแนะนำสำหรับโปรเจกต์ Auto Repair Shop

1. **`@Component`**: ใช้กับ Utility / Helper ที่ไม่ใช่ Business Logic หรือ Data Access เช่น `ExcelGenerator`, `FileStorageHelper`, `QRCodeGenerator`
2. **`@Configuration` + `@Bean`**: ใช้กับ:
   - `AsyncConfig` (ThreadPool)
   - `SecurityConfig` (JWT Filter, PasswordEncoder)
   - `RedisConfig` (RedisTemplate)
   - `KafkaConfig` (KafkaTemplate)
   - `RestTemplateConfig` (RestTemplate)
   - `ObjectMapper` (Jackson)
3. **`@Qualifier`**: ใช้เมื่อมี Bean หลายตัวประเภทเดียวกัน เช่น `RestTemplate` 2 ตัว, `KafkaTemplate` 2 ตัว, `TaskExecutor` 2 ตัว
4. **`@Primary`**: ใช้กำหนด Default Bean เพื่อให้ `@Autowired` ที่ไม่ระบุอะไร ทำงานได้โดยไม่ Error

### แนวปฏิบัติที่ดี (Best Practices)
- ตั้งชื่อ Bean ด้วย `@Bean(name = "specificName")` ให้สื่อความหมาย (เช่น `externalRestTemplate`, `orderKafkaTemplate`)
- ถ้ามีหลาย Bean ใน Config เดียวกัน ให้ใช้ `@Qualifier` ทุกครั้งเพื่อป้องกันความผิดพลาด
- หลีกเลี่ยงการใช้ `@Primary` มากเกินไป เพราะอาจทำให้สับสนว่า Default คือตัวไหน (ใช้แค่ 1 ตัวต่อ Type)

--- 

# อธิบาย Annotation อื่นๆ ที่สำคัญใน Spring Framework (ฉบับสมบูรณ์)

ต่อเนื่องจาก `@Component`, `@Configuration`, `@Bean`, `@Qualifier`, และ `@Primary` ที่เราได้เรียนรู้ไปแล้ว ในโปรเจกต์ Spring Boot ของเรา (ระบบบริหารจัดการอู่ซ่อมรถ) ยังมี Annotation อีก 4 ตัวที่สำคัญมากสำหรับการทำงานในชีวิตจริง:

- **`@Value`** – การ Inject ค่าจากไฟล์ Properties / YAML
- **`@Profile`** – การกำหนด Environment (Dev / Test / Prod)
- **`@Conditional`** – การสร้าง Bean ตามเงื่อนไขที่กำหนด
- **`@Scope`** – การกำหนดขอบเขต (Lifecycle) ของ Bean

เรามาทำความเข้าใจแบบเจาะลึกพร้อมตัวอย่างในระบบของเรากันครับ

---

## 1. @Value (การ Inject ค่าจาก Properties)

### 1.1 ความหมายและจุดประสงค์

**`@Value`** คือ Annotation ที่ใช้ **Inject (แทรก) ค่าจากไฟล์ Properties, YAML, หรือ Environment Variables** เข้าไปในตัวแปรของ Spring Bean

- ใช้แทนการเขียน `@ConfigurationProperties` (แบบง่าย) หรือการอ่าน `Environment` ด้วยโค้ด
- รองรับ **SpEL (Spring Expression Language)** ทำให้สามารถคำนวณค่าหรือดึงจาก System Properties ได้

### 1.2 ทำงานอย่างไร?

Spring จะอ่านไฟล์ `application.properties` หรือ `application.yml` (ตาม Profile ที่ใช้งาน) และแทนที่ `${...}` ด้วยค่าที่กำหนด

### 1.3 ไวยากรณ์พื้นฐาน

| รูปแบบ | คำอธิบาย | ตัวอย่าง |
|--------|----------|----------|
| `@Value("${property.key}")` | ดึงค่าตาม Key ถ้าไม่มีจะ Error | `@Value("${app.name}")` |
| `@Value("${property.key:default_value}")` | ถ้าไม่มี Key ให้ใช้ค่า Default | `@Value("${server.port:8080}")` |
| `@Value("#{spel.expression}")` | ใช้ SpEL เพื่อคำนวณค่า | `@Value("#{systemProperties['user.country']}")` |
| `@Value("${property.key:${fallback.key}}")` | ใช้ Fallback เป็น Key อื่น | `@Value("${app.contact:${admin.email}}")` |

### 1.4 ตัวอย่างในระบบของเรา (ใช้จริง)

#### ตัวอย่างที่ 1: ค่า Configuration ทั่วไป (application.yml)
```yaml
# application.yml (ไฟล์หลัก)
app:
  name: Auto Repair Shop
  version: 2.0.0

server:
  port: ${SERVER_PORT:1080}  # ใช้ Environment Variable หรือ Default 1080

jwt:
  secret: ${JWT_SECRET:defaultSecretKey}
  expiration: 3600000  # 1 ชั่วโมง

email:
  host: smtp.gmail.com
  port: 587
  username: ${EMAIL_USERNAME:noreply@autorepair.com}
  password: ${EMAIL_PASSWORD:secret}
```

```java
package com.template.app.modules.email.infrastructure.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailProperties {

    // ✅ Inject ค่า host จาก properties
    @Value("${email.host}")
    private String host;

    // ✅ Inject ค่า port ถ้าไม่มีให้ใช้ 587
    @Value("${email.port:587}")
    private int port;

    // ✅ Inject username จาก Environment Variable (ถ้าไม่มีใช้ค่า default)
    @Value("${email.username:noreply@autorepair.com}")
    private String username;

    // ✅ Inject password (ใช้ Environment Variable เพื่อความปลอดภัย)
    @Value("${email.password}")
    private String password;

    // ✅ Inject app name และ version
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    // Getters...
}
```

#### ตัวอย่างที่ 2: JWT Configuration
```java
package com.template.app.modules.auth.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:3600000}")  // Default 1 ชั่วโมง
    private long expirationTimeMs;

    @Value("${jwt.refresh-expiration:86400000}")  // Default 24 ชั่วโมง
    private long refreshExpirationMs;

    // ✅ Inject Server Port (เพื่อใช้ใน Filter)
    @Value("${server.port}")
    private int serverPort;
}
```

#### ตัวอย่างที่ 3: การใช้ SpEL (คำนวณค่าหรือดึงจาก System)
```java
@Service
public class UtilityService {

    // ✅ ดึงชื่อผู้ใช้ OS (System Properties)
    @Value("#{systemProperties['user.name']}")
    private String osUsername;

    // ✅ คำนวณค่าจาก Environment Variable
    @Value("#{systemEnvironment['JAVA_HOME']}")
    private String javaHome;

    // ✅ รวมค่าสองตัวเข้าด้วยกัน
    @Value("#{'${app.name}' + ' v' + '${app.version}'}")
    private String fullAppName;

    // ✅ ใช้ SpEL เพื่อเปลี่ยน String เป็น List (แยกด้วย comma)
    @Value("#{'${app.admin.emails:admin@example.com}'.split(',')}")
    private List<String> adminEmails;

    public void printInfo() {
        System.out.println("Running as: " + osUsername);
        System.out.println("Java Home: " + javaHome);
        System.out.println("App: " + fullAppName);
        System.out.println("Admins: " + adminEmails);
    }
}
```

### 1.5 ข้อควรระวัง (Common Pitfalls)

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Typo (พิมพ์ผิด)** | `@Value("${email.hostt}")` (พิมพ์ hOSSt ผิด) → Spring จะ Error ตั้งแต่ Start | ตรวจสอบ Key ให้ตรงกับไฟล์ Properties ทุกครั้ง |
| **ใช้กับ `static` Field** | `@Value` ใช้กับ `static` ไม่ได้ (เพราะ Spring Inject หลัง Static ถูกโหลด) | เปลี่ยนเป็น `private` (Non-static) |
| **Encoding Issues** | อ่านค่าภาษาไทยแล้วขึ้น `????` | ตรวจสอบว่าไฟล์ Properties ใช้ `UTF-8` และ `spring.banner.charset=UTF-8` |
| **Late Injection** | ถ้าใช้ใน Constructor และพยายามใช้ `@Value` ก่อนที่ Spring จะ Inject เสร็จ | ใช้ Constructor Injection ด้วย `@Value` ใน Parameter ได้ (`public MyClass(@Value("${key}") String val)`) |

---

## 2. @Profile (การกำหนด Environment)

### 2.1 ความหมายและจุดประสงค์

**`@Profile`** คือ Annotation ที่ใช้บอก Spring ว่า **"Bean หรือ Configuration นี้จะถูกสร้าง (Active) เฉพาะเมื่อ Profile ที่ระบุถูกเปิดใช้งานเท่านั้น"**

- ช่วยแยก Configuration ตาม Environment (Development, Test, Production)
- Spring จะอ่าน Profile จาก `spring.profiles.active` (ใน `application.yml` หรือ Environment Variable)

### 2.2 ทำงานอย่างไร?

1. ระบบอ่าน `spring.profiles.active` (เช่น `dev`, `prod`, `test`)
2. Spring จะสร้าง Bean ที่มี `@Profile` ตรงกับค่า Active เท่านั้น
3. Bean ที่ไม่มี `@Profile` จะถูกสร้างเสมอ (Active ทุก Profile)

### 2.3 Profile ทั้ง 3 ของเรา

| Profile | ไฟล์ | การใช้งาน |
|---------|------|----------|
| **`dev`** | `application-dev.yml` | Development (ใช้ H2 Database, Logging เยอะ) |
| **`test`** | `application-test.yml` | Unit Test / Integration Test (ใช้ TestContainers) |
| **`prod`** | `application-prod.yml` | Production (ใช้ PostgreSQL, Logging น้อย, Performance Optimized) |

### 2.4 ตัวอย่างในระบบของเรา (ใช้จริง)

#### ตัวอย่างที่ 1: แยก Database Configuration
```java
package com.template.app.configuration.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    // ✅ ใช้ H2 Database ใน DEV (สะดวก ไม่ต้องติดตั้ง)
    @Bean
    @Profile("dev")  // ✅ Active เฉพาะตอนรันด้วย -Dspring.profiles.active=dev
    public DataSource devDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:autorepair;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    // ✅ ใช้ PostgreSQL ใน PROD (ของจริง)
    @Bean
    @Profile("prod")  // ✅ Active เฉพาะ Production
    public DataSource prodDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/autorepair");
        dataSource.setUsername("admin");
        dataSource.setPassword("${DB_PASSWORD}");  // Environment Variable
        return dataSource;
    }
}
```

#### ตัวอย่างที่ 2: เปิด/ปิด Logging (AOP) ตาม Environment
```java
package com.template.app.configuration.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile({"dev", "test"})  // ✅ เปิด AOP เฉพาะ DEV และ TEST (Performance ไม่สำคัญ)
public class SystemMonitor {

    @Around("execution(* com.template.app.modules..application..*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("🔍 Calling: " + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("✅ Finished in: " + (end - start) + "ms");
        return result;
    }
}
```
> ใน Production (`prod`) Class นี้จะไม่ถูกสร้างเป็น Bean เลย ทำให้ไม่มีการ Log ทุก Method (ช่วยลด Overhead)

#### ตัวอย่างที่ 3: ใช้กับ Service (เปิดใช้งาน Mock หรือจริง)
```java
package com.template.app.modules.email.application.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// ✅ ใน DEV ใช้ EmailService ที่พิมพ์ Log อย่างเดียว (ไม่ส่งจริง)
@Service
@Profile("dev")
public class DevEmailService implements EmailService {
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("📧 [DEV MODE] Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}

// ✅ ใน PROD ใช้ EmailService ที่ส่งจริง (SMTP)
@Service
@Profile("prod")
public class ProdEmailService implements EmailService {
    @Override
    public void sendEmail(String to, String subject, String body) {
        // ส่ง Email จริงผ่าน SMTP
        smtpClient.send(to, subject, body);
    }
}
```

#### ตัวอย่างที่ 4: การเปิดใช้งานใน Application.java
```java
package com.template.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // ✅ กำหนด Profile ผ่าน Environment Variable หรือ JVM Argument
        // -Dspring.profiles.active=dev
        SpringApplication.run(Application.class, args);
    }
}
```

### 2.5 การตั้งค่า Profile ใน `application.yml`
```yaml
# application.yml (ไฟล์หลัก)
spring:
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:dev}  # ถ้าไม่มี Environment Variable ให้ใช้ dev

---
# application-dev.yml (แยกไฟล์)
spring:
  config:
    activate:
      on-profile: dev
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: create-drop
```

---

## 3. @Conditional (การสร้าง Bean ตามเงื่อนไข)

### 3.1 ความหมายและจุดประสงค์

**`@Conditional`** คือ Annotation ที่ใช้บอก Spring ว่า **"Bean นี้จะถูกสร้างก็ต่อเมื่อเงื่อนไข (Condition) ที่กำหนดเป็นจริงเท่านั้น"**

- เป็นพื้นฐานของ Auto-Configuration ใน Spring Boot
- มีลูกหลานมากมายที่ใช้บ่อย เช่น `@ConditionalOnProperty`, `@ConditionalOnClass`, `@ConditionalOnMissingBean`

### 3.2 @ConditionalOnProperty (ที่ใช้บ่อยที่สุด)

สร้าง Bean ก็ต่อเมื่อ Property ที่ระบุมีค่าตามที่กำหนด

#### ตัวอย่าง: เปิด/ปิด Feature OCR (Image to Text)
```yaml
# application.yml
app:
  ocr:
    enabled: true  # ✅ ถ้า true จะเปิด OCR, ถ้า false จะปิด
    provider: tesseract
```

```java
package com.template.app.modules.ocr.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OCRConfig {

    // ✅ สร้าง Bean เฉพาะเมื่อ app.ocr.enabled = true
    @Bean
    @ConditionalOnProperty(name = "app.ocr.enabled", havingValue = "true")
    public OCRService ocrService() {
        return new TesseractOCRService();
    }

    // ✅ ถ้า ocr.enabled = false จะไม่สร้าง Bean นี้ (ใช้ Service ที่ไม่ทำอะไร)
    @Bean
    @ConditionalOnProperty(name = "app.ocr.enabled", havingValue = "false", matchIfMissing = true)
    public OCRService noopOcrService() {
        return new NoopOCRService(); // Service ที่ return ข้อความว่าง
    }
}
```

#### ตัวอย่าง: ใช้กับ Notification (เปิดเฉพาะ Production)
```java
@Component
@ConditionalOnProperty(
    name = "app.notification.enabled", 
    havingValue = "true", 
    matchIfMissing = false  // ถ้าไม่ระบุ = false = ปิด
)
public class LineNotificationService {
    // ส่ง Line Notify
}

// ถ้า property = false หรือไม่มี จะใช้ Service นี้แทน
@Component
@ConditionalOnProperty(name = "app.notification.enabled", havingValue = "false", matchIfMissing = true)
public class NoopNotificationService {
    // ไม่ทำอะไร
}
```

### 3.3 @ConditionalOnClass และ @ConditionalOnMissingBean

| Annotation | ความหมาย | ตัวอย่าง |
|-----------|----------|----------|
| **`@ConditionalOnClass`** | สร้าง Bean ถ้ามี Class นี้อยู่ใน Classpath | ถ้ามี `io.micrometer.core.instrument.MeterRegistry` → สร้าง Metrics Bean |
| **`@ConditionalOnMissingBean`** | สร้าง Bean ถ้ายังไม่มี Bean ประเภทนี้ใน Container (ป้องกันการ Override) | สร้าง `ObjectMapper` Default ถ้ายังไม่มีใครสร้างไว้ |

#### ตัวอย่าง: ConditionalOnMissingBean (ป้องกันการ Override)
```java
@Configuration
public class JacksonConfig {

    // ✅ ถ้าไม่มี ObjectMapper Bean ใน Container ให้สร้างตัวนี้
    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
```
> ถ้า Developer สร้าง `ObjectMapper` ของตัวเอง (ด้วย `@Bean`) Spring จะไม่สร้างตัวนี้ ทำให้สามารถ Customize ได้ง่าย

#### ตัวอย่าง: ConditionalOnClass (ใช้เฉพาะเมื่อมี Library)
```java
@Configuration
@ConditionalOnClass(name = "org.apache.kafka.clients.producer.KafkaProducer")
public class KafkaAutoConfiguration {
    // ถ้ามี Kafka Library อยู่ใน Project → สร้าง Bean อัตโนมัติ
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        // ...
    }
}
```

---

## 4. @Scope (การกำหนดขอบเขตของ Bean)

### 4.1 ความหมายและจุดประสงค์

**`@Scope`** คือ Annotation ที่ใช้กำหนด **ขอบเขต (Lifecycle)** ของ Bean ว่าแต่ละ Instance จะถูกสร้างเมื่อไหร่ และอยู่ได้นานแค่ไหน

- ใช้กับ `@Component`, `@Service`, `@Bean`
- ค่าเริ่มต้นคือ `Singleton` (ไม่ต้องเขียน)

### 4.2 ประเภทของ Scope (ที่พบบ่อย)

| Scope | คำอธิบาย | ตัวอย่างการใช้งาน |
|-------|----------|------------------|
| **`Singleton` (ค่าเริ่มต้น)** | มี Instance **เดียว** ใน Container ใช้ร่วมกันทุก Request | Service, Repository, Controller |
| **`Prototype`** | สร้าง Instance **ใหม่ทุกครั้ง** ที่ถูก Inject หรือเรียก `getBean()` | Stateful Object (เช่น Shopping Cart) |
| **`Request`** | สร้าง Instance **1 ตัวต่อ HTTP Request** | Object ที่เก็บข้อมูลเฉพาะ Request (เช่น UserSession) |
| **`Session`** | สร้าง Instance **1 ตัวต่อ HTTP Session** | Shopping Cart ของผู้ใช้ (แต่ถ้า Distributed ควรใช้ Redis แทน) |
| **`Application`** | สร้าง Instance **1 ตัวต่อ ServletContext** | ข้อมูลที่ใช้ร่วมกันทั้งแอป (คล้าย Singleton แต่ต่าง Context) |

### 4.3 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: Singleton (ค่าเริ่มต้น) – ใช้กับ Service
```java
@Service
@Scope("singleton")  // ✅ ไม่ต้องเขียนก็ได้ (Default)
public class JobServiceImpl {
    // Bean ตัวนี้มีแค่ตัวเดียวในระบบ ใช้ร่วมกันทุก Request
}
```

#### ตัวอย่างที่ 2: Prototype – Shopping Cart (Stateful)
```java
package com.template.app.modules.weborder.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")  // ✅ ทุกครั้งที่ขอ Cart จะได้คนละ Instance
public class ShoppingCart {
    private List<CartItem> items = new ArrayList<>();
    private String sessionId;

    public void addItem(CartItem item) {
        items.add(item);
    }

    public List<CartItem> getItems() {
        return items;
    }
}
```

```java
@Service
public class CartService {

    @Autowired
    private ApplicationContext context;  // ใช้เพื่อขอ Prototype Bean

    public ShoppingCart createNewCart(String sessionId) {
        // ✅ ขอ Prototype Bean ใหม่ทุกครั้ง
        ShoppingCart cart = context.getBean(ShoppingCart.class);
        cart.setSessionId(sessionId);
        return cart;
    }
}
```

#### ตัวอย่างที่ 3: Request Scope – เก็บข้อมูล User ที่ Login
```java
package com.template.app.modules.auth.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
// ✅ สร้าง Bean ใหม่ทุก Request (ใช้ Proxy เพื่อ Inject เข้า Singleton Service)
public class RequestUserContext {
    private String userId;
    private String whitelabelId;
    private String requestId;

    // Getters & Setters
}
```

```java
@Service
public class AuditService {

    @Autowired
    private RequestUserContext requestUserContext;  // ✅ Inject Request Scope Bean

    public void logAction(String action) {
        System.out.println("User: " + requestUserContext.getUserId() + 
                           " performed " + action);
    }
}
```

#### ตัวอย่างที่ 4: Session Scope – Cart ของ User
```java
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionCart {
    private List<CartItem> items = new ArrayList<>();
    
    // เพิ่ม/ลดสินค้า
}
```
> **ข้อควรระวัง**: Session Scope ไม่เหมาะกับระบบที่ Scale ออกไปหลายเครื่อง (Horizontal Scaling) เพราะ Session จะอยู่แค่เครื่องเดียว ถ้าต้องการให้รองรับหลายเครื่อง ควรใช้ Redis หรือ Database แทน

### 4.4 Proxy Mode (สำคัญ)

เมื่อ Inject `Request` หรือ `Session` Scope เข้าไปใน `Singleton` Service (ซึ่งเป็น Proxy) ต้องใช้ `proxyMode = ScopedProxyMode.TARGET_CLASS` เพื่อให้ Spring สร้าง Proxy Object ที่จะดึง Request/Session จริงๆ ตอน runtime

```java
@Scope(
    value = WebApplicationContext.SCOPE_REQUEST,
    proxyMode = ScopedProxyMode.TARGET_CLASS  // ✅ จำเป็นเพื่อให้ Inject ไปยัง Singleton ได้
)
```

---

## 5. สรุปตารางเปรียบเทียบ Annotation ทั้ง 4 ตัว

| Annotation | ใช้ที่ไหน | หน้าที่หลัก | ตัวอย่างการใช้งานในระบบ |
|-----------|---------|------------|------------------------|
| **`@Value`** | Field, Constructor Parameter | Inject ค่าจาก Properties / YAML | `@Value("${jwt.secret}")` |
| **`@Profile`** | Class หรือ Method | กำหนดว่า Bean นี้ Active เฉพาะ Environment ที่กำหนด | `@Profile("prod")` |
| **`@Conditional`** | Class หรือ Method | สร้าง Bean ก็ต่อเมื่อเงื่อนไขเป็นจริง | `@ConditionalOnProperty(name="ocr.enabled", havingValue="true")` |
| **`@Scope`** | Class หรือ Method | กำหนด Lifecycle ของ Bean | `@Scope("prototype")` |

---

## 6. การทำงานร่วมกันแบบครบวงจรในโปรเจกต์ของเรา

```yaml
# application.yml
app:
  ocr:
    enabled: true
  notification:
    enabled: false

spring:
  profiles:
    active: dev
```

```java
@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    @Scope("prototype")
    public ShoppingCart shoppingCart() {
        return new ShoppingCart();
    }

    @Bean
    @ConditionalOnProperty(name = "app.ocr.enabled", havingValue = "true")
    public OCRService ocrService() {
        return new TesseractOCRService();
    }
}
```

เมื่อรันด้วย `-Dspring.profiles.active=dev`:
1. `DevConfig` ถูกสร้าง
2. `ShoppingCart` เป็น Prototype (สร้างใหม่ทุกครั้งที่ขอ)
3. `ocrService` ถูกสร้าง (เพราะ `ocr.enabled = true`)
4. `notification` ถูกปิด (`enabled = false`)

---

## 7. คำแนะนำสำหรับโปรเจกต์ Auto Repair Shop

1. **`@Value`**: ใช้สำหรับค่าที่ต้องการเปลี่ยนแปลงตาม Environment เช่น:
   - Database URL, Username, Password
   - JWT Secret, Expiration
   - Email Host, Port
   - External API Endpoints

2. **`@Profile`**: ใช้แยก Config สำหรับ 3 Environment:
   - `dev`: H2 Database, Logging เยอะ, Mock External API
   - `test`: TestContainers, Fast Fail
   - `prod`: PostgreSQL, Performance Tuning, Real External API

3. **`@ConditionalOnProperty`**: ใช้เปิด/ปิด Feature แบบยืดหยุ่น:
   - OCR (เปิด/ปิดตาม License)
   - Email Notification (เปิด/ปิดตามความต้องการ)
   - Cache (เปิด Redis ใน Prod, ปิดใน Dev)

4. **`@Scope`**:
   - `Singleton`: Service, Repository, Controller (ค่าเริ่มต้น)
   - `Prototype`: Shopping Cart, Report Request (Stateful)
   - `Request`: User Session Context (เก็บข้อมูล Request)
   - หลีกเลี่ยง `Session` Scope ถ้าระบบเป็น Microservices หรือ Distributed

--- 

# อธิบาย Annotation อื่นๆ ที่สำคัญใน Spring Framework (ฉบับสมบูรณ์)

ต่อเนื่องจาก `@Value`, `@Profile`, `@Conditional`, และ `@Scope` ที่เราได้เรียนรู้ไปแล้ว ในโปรเจกต์ Spring Boot ของเรา (ระบบบริหารจัดการอู่ซ่อมรถ) ยังมี Annotation อีก 4 ตัวที่สำคัญมากสำหรับการทำงานในระบบระดับ Enterprise:

- **`@Scheduled`** – การทำงานตามตารางเวลา (Cron Jobs)
- **`@Cacheable` / `@CacheEvict`** – การจัดการ Cache (Redis)
- **`@Retryable`** – การลองทำงานใหม่เมื่อเกิดข้อผิดพลาด (Retry)
- **`@ControllerAdvice`** – การจัดการ Exception แบบรวมศูนย์ (Global Exception Handler)

เรามาทำความเข้าใจแบบเจาะลึกพร้อมตัวอย่างในระบบของเรากันครับ

---

## 1. @Scheduled (งานตามกำหนดเวลา / Cron Jobs)

### 1.1 ความหมายและจุดประสงค์

**`@Scheduled`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้เมธอดนี้ทำงานอัตโนมัติตามตารางเวลาที่กำหนด"**

- ใช้กับเมธอดใน Service หรือ Component Layer
- ต้องเปิดใช้งานด้วย `@EnableScheduling` บน Configuration Class หรือ Main Application
- รองรับการกำหนดเวลาหลายรูปแบบ: Cron Expression, Fixed Delay, Fixed Rate

### 1.2 ทำงานอย่างไร?

1. Spring จะสแกนหาเมธอดที่มี `@Scheduled` (หลังจากเปิด `@EnableScheduling`)
2. สร้าง Task Scheduler (ใช้ Thread Pool) เพื่อเรียกเมธอดตามเวลาที่กำหนด
3. ทำงานแบบ Asynchronous (ไม่รอ) โดยค่าเริ่มต้น

### 1.3 รูปแบบการกำหนดเวลา

| รูปแบบ | คำอธิบาย | ตัวอย่าง |
|--------|----------|----------|
| **Cron Expression** | ใช้รูปแบบ Cron (6 fields) | `@Scheduled(cron = "0 30 6 * * *")` = ทุกวัน 06:30 |
| **Fixed Delay** | รอให้งานเสร็จก่อน แล้วรออีก X ms | `@Scheduled(fixedDelay = 60000)` = รอ 60 วิ หลังงานเสร็จ |
| **Fixed Rate** | ทำงานทุก X ms (ไม่รอให้งานเสร็จ) | `@Scheduled(fixedRate = 300000)` = ทุก 5 นาที (ไม่รอ) |
| **Initial Delay** | รอ X ms ก่อนเริ่มครั้งแรก | `@Scheduled(initialDelay = 10000, fixedDelay = 60000)` |

### 1.4 ตัวอย่างในระบบของเรา (ใช้งานจริง)

#### ตัวอย่างที่ 1: Batch Job ทั้ง 6 งาน (Cron Expression)

```java
package com.template.app.modules.batch.infrastructure.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling  // ✅ เปิดใช้งาน Scheduling
public class BatchScheduler {

    private final BatchJobExecutor batchJobExecutor;

    public BatchScheduler(BatchJobExecutor batchJobExecutor) {
        this.batchJobExecutor = batchJobExecutor;
    }

    /*
        batch001: ส่งอีเมลแจ้งเตือนรายวัน
        บรรทัดคำอธิบาย: Executes daily at 6:30 AM to send daily email notifications.
    */
    @Scheduled(cron = "0 30 6 * * *")
    public void executeBatch001() {
        batchJobExecutor.executeJob("batch001");
    }

    /*
        batch002: สร้างรายงานประจำวัน
        บรรทัดคำอธิบาย: Executes daily at 6:45 AM to generate daily reports.
    */
    @Scheduled(cron = "0 45 6 * * *")
    public void executeBatch002() {
        batchJobExecutor.executeJob("batch002");
    }

    /*
        batch003: อัปเดตสถานะงานค้าง
        บรรทัดคำอธิบาย: Executes daily at 6:30 AM to update pending job statuses.
    */
    @Scheduled(cron = "0 30 6 * * *")
    public void executeBatch003() {
        batchJobExecutor.executeJob("batch003");
    }

    /*
        batch004: ล้างข้อมูล/ซิงค์ฐานข้อมูล (กลางคืน)
        บรรทัดคำอธิบาย: Executes daily at 3:00 AM for cleanup and database sync.
    */
    @Scheduled(cron = "0 0 3 * * *")
    public void executeBatch004() {
        batchJobExecutor.executeJob("batch004");
    }

    /*
        batch005: ซิงค์ข้อมูล Realtime (ทุก 30 นาที)
        บรรทัดคำอธิบาย: Executes every 30 minutes for real-time data sync.
    */
    @Scheduled(cron = "0 0/30 * * * *")
    public void executeBatch005() {
        batchJobExecutor.executeJob("batch005");
    }

    /*
        batch006: ส่งสรุปยอดขาย
        บรรทัดคำอธิบาย: Executes daily at 6:30 AM to send daily sales summary.
    */
    @Scheduled(cron = "0 30 6 * * *")
    public void executeBatch006() {
        batchJobExecutor.executeJob("batch006");
    }
}
```

#### ตัวอย่างที่ 2: ใช้ Fixed Delay + Initial Delay (งานหลังระบบเริ่ม)

```java
@Component
public class CacheWarmupService {

    /*
        ฟังก์ชันนี้รันหลังจากระบบเริ่มทำงาน 10 วินาที แล้วรันซ้ำทุก 1 ชั่วโมง
        บรรทัดคำอธิบาย: Executes 10 seconds after startup, then repeats every 1 hour.
    */
    @Scheduled(initialDelay = 10000, fixedDelay = 3600000)
    public void warmupCache() {
        // โหลดข้อมูลที่ใช้บ่อยเข้า Cache (เช่น รายการอะไหล่ยอดนิยม)
        System.out.println("🔥 Warming up cache...");
        cacheService.loadTopPartsIntoCache();
        cacheService.loadCustomerListIntoCache();
    }
}
```

#### ตัวอย่างที่ 3: ใช้ Fixed Rate (ทำงานทุก 5 นาที)

```java
@Component
public class HealthCheckService {

    /*
        ฟังก์ชันนี้ตรวจสอบสถานะระบบทุก 5 นาที
        บรรทัดคำอธิบาย: Checks system health every 5 minutes.
    */
    @Scheduled(fixedRate = 300000)  // 5 นาที (ไม่รอให้งานเสร็จ)
    public void checkSystemHealth() {
        // ตรวจสอบการเชื่อมต่อ Database, Redis, Kafka
        boolean dbHealthy = databaseService.checkConnection();
        boolean redisHealthy = redisService.checkConnection();
        
        if (!dbHealthy || !redisHealthy) {
            // ส่งแจ้งเตือนไปยัง Admin
            notificationService.sendAlert("System unhealthy: DB=" + dbHealthy + ", Redis=" + redisHealthy);
        }
    }
}
```

#### ตัวอย่างที่ 4: การใช้ Thread Pool สำหรับ Scheduled Tasks

```java
package com.template.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        // ✅ ใช้ Thread Pool ขนาด 10 สำหรับงาน Scheduled
        return Executors.newScheduledThreadPool(10);
    }
}
```

### 1.5 ข้อควรระวัง (Common Pitfalls)

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Cron Expression ผิด** | เขียน Cron ผิด เช่น ใส่แค่ 5 fields (ต้องมี 6 fields) | ใช้ 6 fields: `초 분 시 일 월 요일` |
| **งานใช้เวลานานเกินไป** | งานใช้เวลานานกว่า Interval ถัดไป → งานซ้อนทับกัน | ใช้ `fixedDelay` แทน `fixedRate` เพื่อรอให้งานเสร็จก่อน |
| **Exception ไม่ถูกจับ** | ถ้าเกิด Exception ใน `@Scheduled` งานถัดไปจะไม่ทำงาน | ใช้ `try-catch` ภายในเมธอด หรือใช้ `@Async` แยก Thread |
| **Self-Invocation** | เมธอด `@Scheduled` เรียกเมธอดอื่นในคลาสเดียวกัน → ไปไม่ถึง Proxy | แยกไปอีก Service หรือใช้ `@Autowired` Self Injection |

---

## 2. @Cacheable / @CacheEvict (การจัดการ Cache)

### 2.1 ความหมายและจุดประสงค์

**`@Cacheable`** และ **`@CacheEvict`** คือ Annotation ที่ใช้จัดการ **Caching (การเก็บข้อมูลในหน่วยความจำ)** เพื่อลดภาระการเรียก Database

| Annotation | หน้าที่ |
|-----------|---------|
| **`@Cacheable`** | เก็บผลลัพธ์ของเมธอดไว้ใน Cache ถ้ามีการเรียกเมธอดนี้ด้วย Parameter เดิม → คืนค่าจาก Cache (ไม่เรียก DB) |
| **`@CacheEvict`** | ลบข้อมูลออกจาก Cache (ใช้เมื่อมีการ Update/Delete ข้อมูล) |
| **`@CachePut`** | อัปเดต Cache โดยไม่ตรวจสอบว่ามีอยู่หรือไม่ (ใช้เมื่อต้องการ Refresh Cache) |

### 2.2 ทำงานอย่างไร?

1. Spring สร้าง Proxy ของ Service ที่มี `@Cacheable`
2. ก่อนเรียกเมธอดจริง Proxy ตรวจสอบ Cache ว่า Key (จาก Parameter) มีอยู่หรือไม่?
   - ถ้ามี → คืนค่าจาก Cache (ไม่เรียกเมธอด)
   - ถ้าไม่มี → เรียกเมธอดจริง → เก็บผลลัพธ์ใน Cache → คืนค่า
3. ใช้ Redis หรือ EhCache หรือ Caffeine เป็น Cache Provider

### 2.3 ตัวอย่างในระบบของเรา (ใช้งานจริง)

#### ตัวอย่างที่ 1: @Cacheable สำหรับ Customer (ค้นหาบ่อย)

```java
package com.template.app.modules.customer.infrastructure.cache;

import com.template.app.modules.customer.domain.MCustomer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerCacheService {

    /*
        ฟังก์ชันนี้ดึงข้อมูลลูกค้าจาก Cache
        - cache name: "customers"
        - key: customerId
        บรรทัดคำอธิบาย: Retrieves customer data from cache using customerId as key.
    */
    @Cacheable(value = "customers", key = "#customerId")
    public MCustomer getCustomer(UUID customerId) {
        // เมธอดนี้จะถูกเรียกก็ต่อเมื่อไม่มีข้อมูลใน Cache
        // Normally, this would call the repository.
        return null;
    }

    /*
        ฟังก์ชันนี้ดึงข้อมูลลูกค้าจากหมายเลขโทรศัพท์
        - cache name: "customer_phone"
        - key: phone
        บรรทัดคำอธิบาย: Retrieves customer data from cache using phone number as key.
    */
    @Cacheable(value = "customer_phone", key = "#phone")
    public MCustomer getCustomerByPhone(String phone) {
        return null;
    }

    /*
        ฟังก์ชันนี้จะอัปเดต Cache เมื่อมีการบันทึกข้อมูลลูกค้า
        - @CachePut: บังคับอัปเดต Cache ทุกครั้ง
        บรรทัดคำอธิบาย: Updates the cache when customer data is saved.
    */
    @CachePut(value = "customers", key = "#customer.id")
    public MCustomer saveCustomer(MCustomer customer) {
        return customer;
    }

    /*
        ฟังก์ชันนี้ลบข้อมูลลูกค้าออกจาก Cache
        - @CacheEvict: ลบ Cache ตาม key
        บรรทัดคำอธิบาย: Evicts customer data from cache when deleted.
    */
    @CacheEvict(value = {"customers", "customer_phone"}, key = "#customerId")
    public void evictCustomer(UUID customerId) {
        // ลบ Cache ทั้งสองรูปแบบ (ID และ Phone)
    }

    /*
        ฟังก์ชันนี้ลบ Cache ทั้งหมดของลูกค้า
        - allEntries = true: ลบทุก key ใน Cache
        บรรทัดคำอธิบาย: Clears all customer cache entries.
    */
    @CacheEvict(value = {"customers", "customer_phone"}, allEntries = true)
    public void evictAllCustomers() {
        // ลบทุก key ใน caches
    }
}
```

#### ตัวอย่างที่ 2: ใช้ใน Service จริง (Job Service)

```java
package com.template.app.modules.job.application.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl {

    /*
        ฟังก์ชันนี้ดึงข้อมูล Job จาก Cache (ลดภาระ DB)
        Redis Key: jobs:{jobId}
        บรรทัดคำอธิบาย: Retrieves job data from cache. If not found, calls database.
    */
    @Cacheable(value = "jobs", key = "#jobId")
    public TJob getJob(UUID jobId) {
        // ถ้าไม่มีใน Cache → เรียก DB
        return jobRepository.findById(jobId).orElse(null);
    }

    /*
        ฟังก์ชันนี้ดึงข้อมูล Job ตาม Job Number
        ใช้ cache name แยกต่างหากสำหรับการค้นหาบ่อย
        บรรทัดคำอธิบาย: Retrieves job data by job number from cache.
    */
    @Cacheable(value = "job_by_number", key = "#jobNo")
    public TJob getJobByNumber(String jobNo) {
        return jobRepository.findByJobNo(jobNo);
    }

    /*
        เมื่ออัปเดต Job → ลบ Cache เก่า
        บรรทัดคำอธิบาย: Evicts old job cache when updated.
    */
    @CacheEvict(value = {"jobs", "job_by_number"}, key = "#job.id")
    public TJob updateJob(TJob job) {
        return jobRepository.save(job);
    }

    /*
        เมื่อลบ Job → ลบ Cache
        บรรทัดคำอธิบาย: Evicts job cache when deleted.
    */
    @CacheEvict(value = {"jobs", "job_by_number"}, key = "#jobId")
    public void deleteJob(UUID jobId) {
        jobRepository.deleteById(jobId);
    }
}
```

#### ตัวอย่างที่ 3: ใช้กับ Quotation (Cache Complex Data)

```java
@Service
public class QuotationService {

    /*
        ฟังก์ชันนี้ดึงข้อมูลใบเสนอราคาแบบละเอียด (รวมรายการอะไหล่และบริการ)
        ใช้ Cache เพื่อลดการ Join Table ที่ซับซ้อน
        บรรทัดคำอธิบาย: Retrieves detailed quotation data (with parts/services) from cache.
    */
    @Cacheable(value = "quotation_details", key = "#quotationId")
    public QuotationDetailDTO getQuotationDetails(UUID quotationId) {
        // Query ซับซ้อน (Join หลายตาราง)
        return quotationRepository.findDetailById(quotationId);
    }

    /*
        เมื่ออนุมัติ Quotation → ลบ Cache เก่า
        บรรทัดคำอธิบาย: Evicts quotation cache when approved.
    */
    @CacheEvict(value = {"quotation_details", "quotations"}, key = "#quotationId")
    public void approveQuotation(UUID quotationId) {
        quotationRepository.updateStatus(quotationId, "APPROVED");
    }
}
```

#### ตัวอย่างที่ 4: Conditional Caching (Cache เฉพาะบางกรณี)

```java
@Service
public class ReportService {

    /*
        ฟังก์ชันนี้ Cache เฉพาะ Report ที่มีขนาดไม่เกิน 1000 รายการ
        - condition = "#size < 1000": Cache ถ้า size < 1000
        - unless = "#result == null": ไม่ Cache ถ้า result เป็น null
        บรรทัดคำอธิบาย: Caches report only if size is less than 1000.
    */
    @Cacheable(value = "reports", key = "#reportType + ':' + #date",
               condition = "#size < 1000", unless = "#result == null")
    public List<DailySalesDTO> getDailyReport(String reportType, LocalDate date, int size) {
        return reportRepository.findByDateAndType(reportType, date);
    }
}
```

### 2.4 ข้อควรระวัง (Common Pitfalls)

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Cache ไม่ทำงาน** | ลืมเปิด `@EnableCaching` | เพิ่ม `@EnableCaching` ใน Main Application หรือ Config |
| **Self-Invocation** | เมธอดในคลาสเดียวกันเรียกเมธอด `@Cacheable` → Proxy ไม่ทำงาน | แยกไปอีก Service หรือใช้ Self Injection |
| **Key Duplicate** | ใช้ Key ซ้ำกันทำให้ข้อมูลทับกัน | ใช้ `key = "#parameter1 + ':' + #parameter2"` เพื่อให้ไม่ซ้ำ |
| **Cache Poisoning** | เก็บข้อมูลที่ใหญ่เกินไป (Memory Leak) | ใช้ `condition` หรือ `unless` เพื่อจำกัดขนาด |
| **Stale Cache** | Cache ไม่ถูกลบเมื่อข้อมูลเปลี่ยน | ใช้ `@CacheEvict` ทุกครั้งที่มีการ Update/Delete |

---

## 3. @Retryable (การลองใหม่เมื่อเกิดข้อผิดพลาด)

### 3.1 ความหมายและจุดประสงค์

**`@Retryable`** คือ Annotation ที่ใช้บอก Spring ว่า **"ถ้าเมธอดนี้เกิด Exception ที่ระบุ ให้ลองเรียกใหม่ (Retry) ตามจำนวนครั้งที่กำหนด"**

- ใช้กับ Service Method ที่อาจมีข้อผิดพลาดชั่วคราว (Transient Failures) เช่น:
  - Network Timeout (เรียก API ภายนอก)
  - Database Connection Lost (Connection Pool เต็ม)
  - Kafka Send Failed
- ต้องเปิดใช้งานด้วย `@EnableRetry`

### 3.2 ทำงานอย่างไร?

1. Spring สร้าง Proxy ของ Service
2. เมื่อเมธอดเกิด Exception ที่ระบุ (`retryFor`) Proxy จะ:
   - รอตาม `backoff` (หน่วงเวลา)
   - ลองใหม่อีกครั้ง (ตาม `maxAttempts`)
   - ถ้าลองครบแล้วยัง Fail → ส่ง Exception ต่อไป (ให้ Global Handler จัดการ)

### 3.3 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: Retry เมื่อเรียก API ภายนอก (Supplier)

```java
package com.template.app.modules.purchase.application.impl;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SupplierApiService {

    /*
        ฟังก์ชันนี้เรียก API Supplier เพื่อส่ง PO
        ถ้าเกิด Exception หรือ Timeout → ลองใหม่สูงสุด 3 ครั้ง
        - maxAttempts: 3 ครั้ง (รวมครั้งแรก)
        - backoff: รอ 2 วินาที, แล้วคูณ 2 (2, 4, 8 วินาที)
        - retryFor: เกิด Exception ประเภทนี้ถึงจะ Retry
        บรรทัดคำอธิบาย: Calls supplier API with retry on timeout or connection errors.
    */
    @Retryable(
        value = { RuntimeException.class, TimeoutException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public String sendPurchaseOrderToSupplier(String poData) {
        // เรียก Supplier API (อาจใช้เวลานาน)
        return restTemplate.postForObject(
            "https://supplier-api.example.com/po",
            poData,
            String.class
        );
    }

    /*
        ฟังก์ชันนี้ใช้ fallback เมื่อ Retry หมดแล้วยังล้มเหลว
        บรรทัดคำอธิบาย: Fallback method when all retry attempts fail.
    */
    public String fallbackSendPO(String poData) {
        // บันทึกไว้ใน Queue เพื่อส่งทีหลัง
        pendingPOMessageQueue.add(poData);
        return "PO saved to queue for later processing";
    }
}
```

#### ตัวอย่างที่ 2: Retry เมื่อ Database Connection ล้มเหลว

```java
package com.template.app.modules.inventory.infrastructure.repository;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepository {

    /*
        ฟังก์ชันนี้บันทึกการเคลื่อนไหวสินค้า
        ถ้าเกิด DataAccessException (DB Connection Lost) → ลองใหม่
        บรรทัดคำอธิบาย: Saves inventory transaction with retry on database errors.
    */
    @Retryable(
        value = { DataAccessException.class },
        maxAttempts = 2,
        backoff = @Backoff(delay = 1000)
    )
    public TInventory saveTransaction(TInventory transaction) {
        return jpaRepository.save(transaction);
    }
}
```

#### ตัวอย่างที่ 3: Retry เมื่อ Kafka Send Failed

```java
package com.template.app.modules.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /*
        ฟังก์ชันนี้ส่ง Event ไปยัง Kafka
        ถ้าเกิด KafkaException → ลองใหม่ 3 ครั้ง
        บรรทัดคำอธิบาย: Sends event to Kafka with retry on producer failures.
    */
    @Retryable(
        value = { KafkaException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void sendJobEvent(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }
}
```

#### ตัวอย่างที่ 4: ใช้ @Recover เพื่อจัดการเมื่อ Retry หมด

```java
@Service
public class PaymentGatewayService {

    /*
        ฟังก์ชันนี้เรียก Payment Gateway (เช่น Omise, 2C2P)
        บรรทัดคำอธิบาย: Calls payment gateway API with retry on connection errors.
    */
    @Retryable(
        value = { IOException.class, PaymentGatewayException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 5000, multiplier = 2)
    )
    public PaymentResponse processPayment(PaymentRequest request) {
        return paymentGatewayClient.charge(request);
    }

    /*
        ฟังก์ชันนี้จะถูกเรียกเมื่อ Retry ทั้งหมดล้มเหลว
        - ใช้ @Recover เพื่อกำหนด Fallback Logic
        - ต้องมี Parameter เหมือนกับเมธอดหลัก (หรือมี Exception เป็นตัวแรก)
        บรรทัดคำอธิบาย: Recovery method when all retry attempts fail.
    */
    @Recover
    public PaymentResponse recoverPayment(PaymentGatewayException e, PaymentRequest request) {
        // บันทึก Payment ว่า "PENDING" และส่งไป Queue เพื่อประมวลผลทีหลัง
        paymentService.savePendingPayment(request, "Payment gateway unavailable");
        return PaymentResponse.builder()
                .status("PENDING")
                .message("Payment is pending, will be processed later")
                .build();
    }
}
```

### 3.4 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Retry มากเกินไป** | ทำให้ Response ช้าลง (TTFB สูง) | ตั้ง `maxAttempts=3` และ `backoff` ให้เหมาะสม |
| **Idempotency** | ถ้า Retry งานที่ไม่ Idempotent อาจเกิด Duplicate | ตรวจสอบว่าเมธอดสามารถเรียกซ้ำได้ (ใช้ UUID หรือ Transaction ID) |
| **ใช้กับ @Transactional** | `@Retryable` และ `@Transactional` ทำงานร่วมกันอาจมีปัญหา | ใช้ `@Transactional` ไว้ข้างใน (inner method) แทนที่จะใส่ที่เมธอด `@Retryable` |

---

## 4. @ControllerAdvice (Global Exception Handler)

### 4.1 ความหมายและจุดประสงค์

**`@ControllerAdvice`** คือ Annotation ที่ใช้ **จัดการ Exception แบบรวมศูนย์ (Centralized)** สำหรับทุก Controller ในระบบ

- ใช้ร่วมกับ `@ExceptionHandler` เพื่อจับ Exception ประเภทต่างๆ
- ช่วยลดการเขียน `try-catch` ซ้ำๆ ใน Controller ทุกตัว
- รองรับการจัดการ Exception เฉพาะบาง Package หรือ Controller

### 4.2 ทำงานอย่างไร?

1. Spring สร้าง AOP Proxy ที่ Intercept ทุก Request ที่ไปยัง Controller
2. เมื่อ Controller เกิด Exception ที่ไม่ถูกจับใน Controller
3. Spring จะค้นหา `@ControllerAdvice` ที่มี `@ExceptionHandler` สำหรับ Exception นั้น
4. รันเมธอดนั้นและส่ง Response กลับไปยัง Client

### 4.3 ตัวอย่างในระบบของเรา (ใช้งานจริง)

#### ตัวอย่างที่ 1: GlobalExceptionHandler ฉบับสมบูรณ์

```java
package com.template.app.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.template.app.exception.models.*;
import com.template.app.logging.LogService;
import com.template.app.logging.infrastrutcture.ErrorLogSchema;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestControllerAdvice  // ✅ ใช้กับ REST API (จะส่ง Response เป็น JSON)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final LogService logService;

    /*
        ฟังก์ชันนี้จัดการ DomainException (Business Logic Error)
        บรรทัดคำอธิบาย: Handles DomainException for business rule violations.
    */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex, WebRequest request) {
        // บันทึก Log ลง MongoDB (แบบ Async)
        buildErrorLog(ex, MDC.get("userId"), MDC.get("companyId"), 
                     MDC.get("requestId"), MDC.get("methodId"));
        logger.error("Domain error: {}", ex.getMessage());
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .timestamp(new Date())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Bad Request")
                        .message(ex.getMessage())
                        .path(request.getDescription(false))
                        .build());
    }

    /*
        ฟังก์ชันนี้จัดการ ApplicationException (Application Layer Error)
        บรรทัดคำอธิบาย: Handles ApplicationException for service layer errors.
    */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex, WebRequest request) {
        buildErrorLog(ex, MDC.get("userId"), MDC.get("companyId"), 
                     MDC.get("requestId"), MDC.get("methodId"));
        logger.error("Application error: {}", ex.getMessage());
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .timestamp(new Date())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Internal Server Error")
                        .message("Something went wrong. Please contact support.")
                        .path(request.getDescription(false))
                        .build());
    }

    /*
        ฟังก์ชันนี้จัดการ Validation Error (@Valid)
        บรรทัดคำอธิบาย: Handles validation errors from @Valid annotations.
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        buildErrorLog(ex, MDC.get("userId"), MDC.get("companyId"), 
                     MDC.get("requestId"), MDC.get("methodId"));
        
        // ดึงข้อความ Error ทั้งหมดจาก validation
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ValidationErrorResponse.builder()
                        .timestamp(new Date())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Validation Failed")
                        .message("Input validation failed")
                        .errors(errors)
                        .build());
    }

    /*
        ฟังก์ชันนี้จัดการ FailedRequestException (Invalid Request)
        บรรทัดคำอธิบาย: Handles bad request errors.
    */
    @ExceptionHandler(FailedRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(FailedRequestException ex, WebRequest request) {
        buildErrorLog(ex, MDC.get("userId"), MDC.get("companyId"), 
                     MDC.get("requestId"), MDC.get("methodId"));
        logger.error("Bad request: {}", ex.getMessage());
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .timestamp(new Date())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Bad Request")
                        .message(ex.getMessage())
                        .path(request.getDescription(false))
                        .build());
    }

    /*
        ฟังก์ชันนี้จัดการ Exception ทั่วไปที่ยังไม่ถูกจับ
        บรรทัดคำอธิบาย: Catches any unhandled exceptions.
    */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        buildErrorLog(ex, MDC.get("userId"), MDC.get("companyId"), 
                     MDC.get("requestId"), MDC.get("methodId"));
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .timestamp(new Date())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .error("Internal Server Error")
                        .message("An unexpected error occurred. Please contact support.")
                        .path(request.getDescription(false))
                        .build());
    }

    /*
        ฟังก์ชันนี้บันทึก Error Log ลง MongoDB (แบบ Async)
        บรรทัดคำอธิบาย: Asynchronously saves error log to MongoDB.
    */
    @Async
    protected void buildErrorLog(Exception ex, String userId, String companyId, 
                                 String requestId, String methodId) {
        ErrorLogSchema errorLog = new ErrorLogSchema();
        errorLog.setErrorMessage(ex.getMessage());
        errorLog.setErrorStackTrace(formatStackTrace(ex.getStackTrace()));
        errorLog.setTimestamp(new Date());
        errorLog.setUserId(userId);
        errorLog.setCompanyId(companyId);
        errorLog.setRequestId(requestId);
        errorLog.setMethodId(methodId);
        logService.saveErrorLogAsync(errorLog);
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        return Arrays.stream(stackTrace)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
    }
}
```

#### ตัวอย่างที่ 2: DTO Response สำหรับ Error

```java
package com.template.app.exception;

import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}

@Data
@Builder
public class ValidationErrorResponse {
    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private List<String> errors;
}
```

### 4.4 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Exception ซ้อนกัน** | ใช้ `@ExceptionHandler(Exception.class)` ไว้ด้านล่างสุด เพื่อให้ตัวที่เจาะจงกว่าได้ทำงานก่อน | จัดลำดับจาก Specific → Generic |
| **Log ซ้ำ** | ใช้ `@Async` เพื่อไม่ให้ช้า แต่ระวัง Log อาจไม่ถูกบันทึกทันที | ใช้ `@Async` เฉพาะงานที่ไม่จำเป็นต้องทันที |
| **MDC Context** | MDC อาจหายไปใน Thread ที่ Async | ใช้ `@Async` ร่วมกับ `MdcContextPropagator` |

---

## 5. สรุปตารางเปรียบเทียบ Annotation

| Annotation | ใช้ที่ไหน | หน้าที่หลัก | ต้องเปิดใช้งาน |
|-----------|---------|------------|---------------|
| **`@Scheduled`** | Method | ทำงานตามกำหนดเวลา (Cron/FixedDelay) | `@EnableScheduling` |
| **`@Cacheable`** | Method | เก็บผลลัพธ์ใน Cache | `@EnableCaching` |
| **`@CacheEvict`** | Method | ลบ Cache | `@EnableCaching` |
| **`@Retryable`** | Method | ลองทำงานใหม่เมื่อเกิด Exception | `@EnableRetry` |
| **`@Recover`** | Method | Fallback เมื่อ Retry หมด | `@EnableRetry` (ใช้ร่วม) |
| **`@ControllerAdvice`** | Class | จัดการ Exception แบบรวมศูนย์ | ไม่ต้องเปิดใช้งาน |

---

## 6. การทำงานร่วมกันแบบครบวงจร

```java
@Service
public class CompleteService {

    // 1. ทำงานตามเวลา
    @Scheduled(cron = "0 0 6 * * *")
    public void dailyTask() {
        processData();
    }

    // 2. ใช้ Cache
    @Cacheable(value = "data", key = "#id")
    public Data getData(Long id) {
        return repository.findById(id);
    }

    // 3. Retry เมื่อล้มเหลว
    @Retryable(value = { ExternalApiException.class }, maxAttempts = 3)
    public void callExternalApi() {
        restTemplate.getForObject("https://api.example.com", String.class);
    }

    // 4. ถ้า Retry หมด → ใช้ Recover
    @Recover
    public void recoverExternalApi(ExternalApiException e) {
        queueService.addToRetryQueue();
    }
}
```

--- 
# อธิบาย Web Layer Annotation (Presentation Layer) แบบละเอียด

ในระบบ Spring Boot ของเรา (ระบบบริหารจัดการอู่ซ่อมรถ) การทำงานของ **Presentation Layer (Controller)** เป็นจุดที่รับ HTTP Request จาก Client (Web, Mobile) และส่ง Response กลับไปยัง Client Annotation ที่ใช้ใน Layer นี้มีความสำคัญมาก เพราะมันเป็นการกำหนดว่า **REST API** ของเราจะมีหน้าตาเป็นอย่างไร

Annotation ที่เราจะเรียนรู้ในบทนี้ ได้แก่:
- `@RestController`
- `@RequestMapping` (และรูปแบบย่อย `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`)
- `@RequestBody`
- `@ResponseBody`
- `@PathVariable`
- (เพิ่มเติม) `@RequestParam`

เรามาทำความเข้าใจแบบเจาะลึกพร้อมตัวอย่างการใช้งานในโมดูลต่างๆ ของระบบเรากันครับ

---

## 1. @RestController (เครื่องหมายของ REST API)

### 1.1 ความหมายและจุดประสงค์

**`@RestController`** คือ Annotation ที่ใช้บอก Spring ว่า **"คลาสนี้คือ Web Controller สำหรับ REST API"**

- มันคือการรวมกันของ `@Controller` และ `@ResponseBody`
- ทุกเมธอดในคลาสนี้จะคืนค่าเป็น **JSON (หรือ XML)** โดยอัตโนมัติ โดยไม่ต้องเขียน `@ResponseBody` ทุกครั้ง
- ใช้แทน `@Controller` + `@ResponseBody` แบบเก่า

### 1.2 ทำงานอย่างไร?

1. Spring จะสแกนหา `@RestController` และลงทะเบียนเป็น Bean (`@Component` เหมือนกัน)
2. DispatcherServlet จะส่ง Request ไปยังเมธอดที่ถูก Mapping (ด้วย `@RequestMapping`)
3. Spring จะใช้ **HttpMessageConverter** (เช่น `MappingJackson2HttpMessageConverter`) เพื่อแปลง Java Object ที่คืนจากเมธอดให้เป็น JSON
4. ส่ง Response กลับไปยัง Client

### 1.3 ตัวอย่างในระบบของเรา

```java
package com.template.app.modules.job.presentation.controller;

import org.springframework.web.bind.annotation.RestController;  // ✅ import นี้
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@RestController  // ✅ บอก Spring ว่าเป็น REST Controller
@RequestMapping("/api/v1/jobs")  // ✅ กำหนด Base Path
public class JobController {

    // เมธอดทั้งหมดจะคืนค่าเป็น JSON อัตโนมัติ (ไม่ต้องเขียน @ResponseBody)
    
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJob(@PathVariable UUID id) {
        JobResponseDTO response = jobService.getJob(id);
        return ResponseEntity.ok(response);  // ✅ คืนเป็น JSON อัตโนมัติ
    }

    @PostMapping
    public ResponseEntity<JobResponseDTO> createJob(@RequestBody JobCreateRequestDTO request) {
        JobResponseDTO response = jobService.createJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

---

## 2. @RequestMapping และ Forms (Mapping URL)

### 2.1 @RequestMapping (พื้นฐาน)

**`@RequestMapping`** คือ Annotation ที่ใช้ **Mapping (เชื่อมโยง) Request (URL + HTTP Method)** เข้ากับเมธอดใน Controller

- ใช้ได้ทั้งระดับ **Class** (กำหนด Base Path) และระดับ **Method** (กำหนด Path เฉพาะ)
- สามารถระบุ HTTP Method ได้ด้วย `method = RequestMethod.GET`

### 2.2 รูปแบบย่อย (Shortcut Annotations)

| Annotation | เทียบเท่า | ใช้กับ |
|-----------|----------|-------|
| **`@GetMapping`** | `@RequestMapping(method = RequestMethod.GET)` | ดึงข้อมูล (Read) |
| **`@PostMapping`** | `@RequestMapping(method = RequestMethod.POST)` | สร้างข้อมูล (Create) |
| **`@PutMapping`** | `@RequestMapping(method = RequestMethod.PUT)` | แก้ไขข้อมูลทั้งหมด (Update) |
| **`@PatchMapping`** | `@RequestMapping(method = RequestMethod.PATCH)` | แก้ไขข้อมูลบางส่วน (Partial Update) |
| **`@DeleteMapping`** | `@RequestMapping(method = RequestMethod.DELETE)` | ลบข้อมูล (Delete) |

> เรา **ควรใช้ Shortcut Annotations** แทน `@RequestMapping` เพราะอ่านง่ายกว่าและสั้นกว่า

### 2.3 ตัวอย่างในระบบของเรา

#### Controller: CustomerController

```java
package com.template.app.modules.customer.presentation.controller;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")  // ✅ Base Path: /api/v1/customers
public class CustomerController {

    private final CustomerService customerService;

    // GET /api/v1/customers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    // POST /api/v1/customers
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(request));
    }

    // PUT /api/v1/customers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable UUID id,
            @RequestBody CustomerUpdateRequestDTO request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    // DELETE /api/v1/customers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/v1/customers/search?name=John&page=0
    @GetMapping("/search")
    public ResponseEntity<Page<CustomerResponseDTO>> searchCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(customerService.searchCustomers(name, pageable));
    }
}
```

#### Controller: QuotationController (Path ที่ซับซ้อน)

```java
@RestController
@RequestMapping("/api/v1/quotations")
public class QuotationController {

    // GET /api/v1/quotations/job/{jobId}
    @GetMapping("/job/{jobId}")
    public ResponseEntity<QuotationResponseDTO> getQuotationByJob(@PathVariable UUID jobId) {
        return ResponseEntity.ok(quotationService.getQuotationByJob(jobId));
    }

    // PUT /api/v1/quotations/{id}/approve
    @PutMapping("/{id}/approve")
    public ResponseEntity<QuotationResponseDTO> approveQuotation(@PathVariable UUID id) {
        return ResponseEntity.ok(quotationService.approveQuotation(id));
    }

    // GET /api/v1/quotations/report/{id}
    @GetMapping("/report/{id}")
    public ResponseEntity<byte[]> generateReport(@PathVariable UUID id) {
        byte[] pdf = quotationService.generatePDF(id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=quotation_" + id + ".pdf")
                .body(pdf);
    }
}
```

---

## 3. @RequestBody (รับ JSON → Java Object)

### 3.1 ความหมายและจุดประสงค์

**`@RequestBody`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้แปลง JSON (หรือ XML) ใน HTTP Request Body ไปเป็น Java Object"**

- ใช้กับ **Parameter** ของเมธอด Controller
- Spring จะใช้ `HttpMessageConverter` (Jackson) เพื่อแปลง JSON → Object
- ใช้กับ `POST`, `PUT`, `PATCH` ที่มี Body

### 3.2 ทำงานอย่างไร?

1. Client ส่ง Request ด้วย Content-Type: `application/json`
2. Spring อ่าน Body ของ Request
3. Jackson (ObjectMapper) แปลง JSON ไปเป็น DTO Class ที่เราระบุ
4. ถ้า JSON ผิดรูปแบบ หรือ Field ไม่ตรง จะเกิด `HttpMessageNotReadableException` (ถูกจับโดย GlobalExceptionHandler)

### 3.3 ตัวอย่างในระบบของเรา

#### DTO: JobCreateRequestDTO
```java
package com.template.app.modules.job.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class JobCreateRequestDTO {
    
    @NotNull(message = "Customer ID is required")
    private UUID customerId;
    
    @NotNull(message = "Car ID is required")
    private UUID carId;
    
    @NotNull(message = "Mechanic ID is required")
    private UUID mechanicId;
    
    @NotBlank(message = "Symptom is required")
    private String symptom;
    
    private String diagnosisNote;
    private Integer mileage;
}
```

#### Controller: ใช้ @RequestBody
```java
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    // ✅ @RequestBody แปลง JSON Request Body → JobCreateRequestDTO
    @PostMapping
    public ResponseEntity<JobResponseDTO> createJob(
            @RequestBody JobCreateRequestDTO request) {
        // request มีค่า customerId, carId, mechanicId, symptom แล้ว
        JobResponseDTO response = jobService.createJob(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

#### ตัวอย่าง Request (JSON)
```json
POST /api/v1/jobs
Content-Type: application/json

{
    "customerId": "123e4567-e89b-12d3-a456-426614174000",
    "carId": "223e4567-e89b-12d3-a456-426614174001",
    "mechanicId": "323e4567-e89b-12d3-a456-426614174002",
    "symptom": "Engine warning light is on",
    "diagnosisNote": "Check engine code P0300",
    "mileage": 45000
}
```

### 3.4 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **JSON Field ผิด** | ส่ง Field ชื่อ `customer_id` แต่ DTO ใช้ `customerId` (Case Sensitive) | ใช้ `@JsonProperty("customer_id")` ใน DTO หรือใช้ `@JsonNaming` |
| **Missing Field** | JSON ไม่มี Field ที่ DTO ต้องการ → ค่าเป็น null | ใช้ `@NotNull` เพื่อบังคับให้ต้องมี |
| **Extra Field** | JSON มี Field ที่ DTO ไม่มี → Jackson จะ ignore (โดย Default) | ใช้ `@JsonIgnoreProperties(ignoreUnknown = false)` ถ้าต้องการให้ Error |

---

## 4. @ResponseBody (แปลง Object → JSON) – (ไม่ค่อยได้ใช้เพราะมี @RestController)

### 4.1 ความหมายและจุดประสงค์

**`@ResponseBody`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้แปลง Java Object ที่คืนจากเมธอด ไปเป็น JSON (หรือ XML) และใส่ใน HTTP Response Body"**

- ใช้กับ **เมธอด** ใน Controller (หรือใช้กับ Class)
- ถ้าเราใช้ `@RestController` แล้ว **ไม่ต้องเขียน** เพราะมันมีอยู่แล้วใน `@RestController`

### 4.2 เปรียบเทียบ @Controller vs @RestController

```java
// แบบเก่า (ใช้ @Controller + @ResponseBody)
@Controller
public class OldController {
    
    @GetMapping("/user")
    @ResponseBody  // ✅ ต้องเขียนทุกครั้ง
    public User getUser() {
        return new User("John");
    }
}

// แบบใหม่ (ใช้ @RestController)
@RestController  // ✅ มี @ResponseBody ในตัวแล้ว
public class NewController {
    
    @GetMapping("/user")
    public User getUser() {  // ✅ ไม่ต้องเขียน @ResponseBody
        return new User("John");
    }
}
```

### 4.3 ตัวอย่างการใช้งาน (กรณีที่ต้องคืนค่าเป็น String หรือ File)

```java
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    // ✅ คืนค่าเป็น byte[] (ไฟล์ PDF) → ไม่ใช่ JSON
    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> generateReport(@PathVariable UUID id) {
        byte[] pdf = reportService.generatePDF(id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=report.pdf")
                .body(pdf);  // ✅ คืนเป็น binary (ไม่ใช่ JSON)
    }

    // ✅ คืนค่าเป็น String ธรรมดา
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";  // ✅ คืนเป็นข้อความธรรมดา
    }
}
```

---

## 5. @PathVariable (รับค่าจาก URL Path)

### 5.1 ความหมายและจุดประสงค์

**`@PathVariable`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้ดึงค่าจาก URL Template (Path) มาใส่ใน Parameter"**

- ใช้กับ Parameter ของเมธอด Controller
- ต้องตรงกับชื่อใน `{}` ของ `@RequestMapping`

### 5.2 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: ดึง ID จาก URL
```java
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    // GET /api/v1/inventory/part/{partId}
    @GetMapping("/part/{partId}")
    public ResponseEntity<InventorySummaryDTO> getInventoryByPart(
            @PathVariable UUID partId) {  // ✅ ค่า "partId" จะถูก Map ให้
        return ResponseEntity.ok(inventoryService.getInventoryByPart(partId));
    }
}
```

#### ตัวอย่างที่ 2: หลาย Path Variable
```java
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    // GET /api/v1/reports/quotation/{quotationId}/pdf
    @GetMapping("/quotation/{quotationId}/pdf")
    public ResponseEntity<byte[]> getQuotationPdf(
            @PathVariable UUID quotationId) {  // ✅ ใช้ชื่อให้ตรงกับ {}
        return ResponseEntity.ok(reportService.generateQuotationPDF(quotationId));
    }
}
```

#### ตัวอย่างที่ 3: ใช้ชื่อต่างกัน (ถ้าชื่อไม่ตรง)
```java
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    // GET /api/v1/jobs/{jobId}/status
    @GetMapping("/{jobId}/status")
    public ResponseEntity<String> getJobStatus(
            @PathVariable("jobId") UUID id) {  // ✅ ระบุชื่อใน ""
        // ค่าใน URL คือ jobId แต่ Parameter ชื่อ id
        return ResponseEntity.ok(jobService.getStatus(id));
    }
}
```

#### ตัวอย่างที่ 4: Path Variable + Query Parameter
```java
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    // GET /api/v1/customers/{customerId}/jobs?status=CLOSED&page=0
    @GetMapping("/{customerId}/jobs")
    public ResponseEntity<Page<JobResponseDTO>> getCustomerJobs(
            @PathVariable UUID customerId,          // ✅ จาก Path
            @RequestParam(required = false) String status,  // ✅ จาก Query
            @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(customerService.getJobs(customerId, status, page));
    }
}
```

### 5.3 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Type Mismatch** | Path Variable เป็น String แต่ Parameter เป็น UUID | ต้องมั่นใจว่า UUID มาในรูปแบบที่ถูกต้อง (`123e4567...`) |
| **ชื่อไม่ตรงกัน** | `@PathVariable("userId")` แต่ Parameter ชื่อ `id` | ใช้ `@PathVariable("ชื่อในURL")` หรือตั้งชื่อให้ตรงกัน |

---

## 6. @RequestParam (รับค่าจาก Query String) – (เสริม)

### 6.1 ความหมายและจุดประสงค์

**`@RequestParam`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้ดึงค่าจาก Query Parameter (ส่วนหลัง `?` ใน URL)"**

- ใช้กับ Parameter ของเมธอด Controller
- ใช้สำหรับ Pagination, Filtering, Search

### 6.2 ตัวอย่างในระบบของเรา

```java
@RestController
@RequestMapping("/api/v1/quotations")
public class QuotationController {

    // GET /api/v1/quotations?status=PENDING&startDate=2026-01-01&page=0&size=20
    @GetMapping
    public ResponseEntity<Page<QuotationResponseDTO>> listQuotations(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(quotationService.listQuotations(status, startDate, endDate, pageable));
    }
}
```

#### การใช้งาน @RequestParam แบบต่างๆ
```java
@GetMapping("/search")
public ResponseEntity<?> search(
        @RequestParam(required = true) String keyword,      // ✅ ต้องมี (default)
        @RequestParam(required = false) String category,    // ✅ ไม่ต้องมีก็ได้ (null)
        @RequestParam(defaultValue = "0") int page,         // ✅ ถ้าไม่มีใช้ 0
        @RequestParam(defaultValue = "20") int size) {      // ✅ ถ้าไม่มีใช้ 20
    // ...
}
```

#### เรียกใช้งาน
```
GET /api/v1/quotations/search?keyword=engine&category=parts&page=2&size=10
```

---

## 7. สรุปตารางเปรียบเทียบ Annotation

| Annotation | ใช้ที่ไหน | หน้าที่ | ตัวอย่าง |
|-----------|---------|--------|----------|
| **`@RestController`** | Class | บอกว่าเป็น REST Controller (มี @ResponseBody ในตัว) | `@RestController public class JobController` |
| **`@RequestMapping`** | Class / Method | กำหนด URL Mapping | `@RequestMapping("/api/v1/jobs")` |
| **`@GetMapping`** | Method | GET Request | `@GetMapping("/{id}")` |
| **`@PostMapping`** | Method | POST Request | `@PostMapping` |
| **`@PutMapping`** | Method | PUT Request | `@PutMapping("/{id}")` |
| **`@DeleteMapping`** | Method | DELETE Request | `@DeleteMapping("/{id}")` |
| **`@RequestBody`** | Method Parameter | แปลง JSON Body → Java Object | `@RequestBody JobCreateRequestDTO request` |
| **`@ResponseBody`** | Method / Class | แปลง Java Object → JSON (อยู่ใน @RestController แล้ว) | `@ResponseBody public User getUser()` |
| **`@PathVariable`** | Method Parameter | ดึงค่าจาก URL Path | `@PathVariable UUID id` |
| **`@RequestParam`** | Method Parameter | ดึงค่าจาก Query String | `@RequestParam String status` |

---

## 8. ตัวอย่าง Controller ที่สมบูรณ์ (รวมทุก Annotation)

```java
package com.template.app.modules.payment.presentation.controller;

import com.template.app.modules.auth.infrastructure.ratelimit.RateLimit;
import com.template.app.modules.payment.application.interfaces.PaymentService;
import com.template.app.modules.payment.presentation.dto.request.PaymentRecordRequestDTO;
import com.template.app.modules.payment.presentation.dto.response.PaymentResponseDTO;
import com.template.app.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController  // ✅ 1. บอกว่าเป็น REST Controller
@RequestMapping("/api/v1/payments")  // ✅ 2. Base Path
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment Management APIs")
public class PaymentController {

    private final PaymentService paymentService;

    // ✅ POST /api/v1/payments
    @PostMapping
    @Operation(summary = "Record a payment")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PaymentResponseDTO> recordPayment(
            @Valid @RequestBody PaymentRecordRequestDTO request)  // ✅ 3. รับ JSON Body
            throws SystemGlobalException {
        PaymentResponseDTO response = paymentService.recordPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // ✅ 4. คืน JSON
    }

    // ✅ GET /api/v1/payments/{id}
    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PaymentResponseDTO> getPayment(
            @PathVariable UUID id)  // ✅ 5. ดึง ID จาก URL Path
            throws SystemGlobalException {
        PaymentResponseDTO response = paymentService.getPayment(id);
        return ResponseEntity.ok(response);
    }

    // ✅ GET /api/v1/payments?customerId=xxx&status=COMPLETED&page=0&size=10
    @GetMapping
    @Operation(summary = "List payments with filters")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<PaymentResponseDTO>> listPayments(
            @RequestParam(required = false) UUID customerId,  // ✅ 6. Query Parameter
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentResponseDTO> response = paymentService.listPayments(customerId, status, pageable);
        return ResponseEntity.ok(response);
    }

    // ✅ DELETE /api/v1/payments/{id}
    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel payment")
    @RateLimit(limit = 10, duration = 3600, keyType = "USER_ID")
    public ResponseEntity<Void> cancelPayment(
            @PathVariable UUID id,
            @RequestParam String reason) throws SystemGlobalException {  // ✅ Query Parameter
        paymentService.cancelPayment(id, reason);
        return ResponseEntity.noContent().build();  // ✅ 204 No Content
    }
}
```

---

## 9. คำแนะนำสำหรับโปรเจกต์ Auto Repair Shop

1. **ใช้ `@RestController` เสมอ** สำหรับ REST API (ไม่ใช้ `@Controller` + `@ResponseBody` แบบเก่า)
2. **ใช้ Base Path ตาม Version**: `@RequestMapping("/api/v1/...")` เพื่อรองรับ Versioning ในอนาคต
3. **ใช้ Shortcut Annotations** (`@GetMapping`, `@PostMapping`) แทน `@RequestMapping` เพื่อความอ่านง่าย
4. **ใช้ `@Valid` + `@RequestBody`** ร่วมกับ DTO ที่มี `@NotNull`, `@NotBlank` เพื่อ Validate อัตโนมัติ
5. **ใช้ `@PathVariable` สำหรับ ID** และ `@RequestParam` สำหรับ Filter / Pagination
6. **Response Entity** ควรใช้ `ResponseEntity<T>` เพื่อกำหนด HTTP Status Code ให้ชัดเจน (200, 201, 204, 400, 404, 500)

---
 # อธิบาย Web Layer Annotation (ส่วนเสริม) แบบละเอียด

ต่อเนื่องจาก `@RestController`, `@RequestMapping`, `@RequestBody`, `@ResponseBody`, `@PathVariable`, และ `@RequestParam` ที่เราได้เรียนรู้ไปแล้ว ยังมี Annotation ที่สำคัญอีก 4 ตัวที่ใช้ใน Web Layer (Presentation Layer) ซึ่งช่วยให้การเขียน Controller ของเรามีประสิทธิภาพและยืดหยุ่นมากขึ้น:

- **`@ResponseStatus`** – กำหนด HTTP Status Code ที่ต้องการ
- **`@ExceptionHandler`** (ใน Controller) – จัดการ Exception เฉพาะ Controller
- **`@InitBinder`** – กำหนดรูปแบบการ Binding (แปลงข้อมูล)
- **`@ModelAttribute`** – ผูกข้อมูลกับ Model หรือใช้เป็น "Before Advice"

เรามาทำความเข้าใจแบบเจาะลึกพร้อมตัวอย่างการใช้งานในระบบของเรากันครับ

---

## 1. @ResponseStatus (กำหนด HTTP Status Code)

### 1.1 ความหมายและจุดประสงค์

**`@ResponseStatus`** คือ Annotation ที่ใช้บอก Spring ว่า **"เมื่อเมธอดนี้ (หรือ Exception นี้) ถูกเรียก ให้ส่ง HTTP Status Code ที่กำหนดกลับไป"**

- ใช้ได้กับ **เมธอด** ใน Controller (กำหนด Status Code ที่จะส่งกลับ)
- ใช้ได้กับ **คลาส Exception** (กำหนด Status Code ที่จะส่งเมื่อ Exception นี้ถูก thrown)

### 1.2 ทำงานอย่างไร?

1. เมื่อเมธอดใน Controller มี `@ResponseStatus` Spring จะใช้ Status Code ที่กำหนด แทนที่จะเป็น 200 (OK) โดยอัตโนมัติ
2. เมื่อ Exception ที่มี `@ResponseStatus` ถูก thrown, Spring จะจับ Exception แล้วส่ง Status Code ที่กำหนดให้ Client โดยไม่ต้องไปถึง `@ExceptionHandler`

### 1.3 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: ใช้กับ Exception (Custom Exception)

```java
package com.template.app.exception.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// ✅ เมื่อเกิด Exception นี้ ให้ส่ง HTTP 404 (Not Found)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}

// ✅ เมื่อเกิด Exception นี้ ให้ส่ง HTTP 400 (Bad Request)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}

// ✅ เมื่อเกิด Exception นี้ ให้ส่ง HTTP 409 (Conflict)
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
```

#### การใช้งานใน Service

```java
@Service
public class JobService {
    
    public TJob getJob(UUID jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));
        // ✅ HTTP 404 จะถูกส่งอัตโนมัติ โดยไม่ต้องไปถึง GlobalExceptionHandler
    }
    
    public void createJob(JobCreateRequestDTO request) {
        if (jobRepository.existsByJobNo(request.getJobNo())) {
            throw new DuplicateResourceException("Job number already exists: " + request.getJobNo());
            // ✅ HTTP 409 จะถูกส่งอัตโนมัติ
        }
        // ...
    }
}
```

#### ตัวอย่างที่ 2: ใช้กับ Controller Method (กำหนด Status Code เฉพาะ)

```java
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    // ✅ เมื่อสร้าง Job สำเร็จ ให้ส่ง HTTP 201 (Created)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponseDTO createJob(@RequestBody JobCreateRequestDTO request) {
        return jobService.createJob(request);
    }

    // ✅ เมื่อลบ Job สำเร็จ ให้ส่ง HTTP 204 (No Content)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable UUID id) {
        jobService.deleteJob(id);
    }
    
    // ✅ เมื่ออัปเดต Job สำเร็จ ให้ส่ง HTTP 200 (OK) (เป็นค่าเริ่มต้นอยู่แล้ว)
    @PutMapping("/{id}")
    public JobResponseDTO updateJob(@PathVariable UUID id, @RequestBody JobUpdateRequestDTO request) {
        return jobService.updateJob(id, request);
    }
}
```

#### ตัวอย่างที่ 3: ใช้กับ @ExceptionHandler ใน Controller (ก็ได้)

```java
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    // ✅ เมื่อเกิด IllegalArgumentException ให้ส่ง HTTP 400
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }
}
```

---

## 2. @ExceptionHandler (ใน Controller) – การจัดการ Exception เฉพาะ Controller

### 2.1 ความหมายและจุดประสงค์

**`@ExceptionHandler`** คือ Annotation ที่ใช้บอก Spring ว่า **"เมธอดนี้จะจัดการ Exception ประเภทที่ระบุ เมื่อเกิดภายใน Controller นี้"**

- ใช้กับ **เมธอด** ใน Controller
- ต่างจาก `@ControllerAdvice` (Global) ตรงที่ `@ExceptionHandler` ใน Controller **จัดการเฉพาะ Controller นั้นๆ**
- ใช้เมื่อต้องการจัดการ Exception ที่แตกต่างกันในแต่ละ Controller (ไม่ต้องการให้ Global จัดการทั้งหมด)

### 2.2 เปรียบเทียบ @ControllerAdvice vs @ExceptionHandler ใน Controller

| คุณสมบัติ | `@ControllerAdvice` (Global) | `@ExceptionHandler` (ใน Controller) |
|-----------|------------------------------|-------------------------------------|
| **ขอบเขต** | ทุก Controller ในระบบ | เฉพาะ Controller ที่มีเมธอดนี้ |
| **ใช้เมื่อ** | Exception ที่เกิดขึ้นทุกที่ ต้องการจัดการแบบเดียวกัน | Exception ที่ต้องการจัดการเฉพาะตัว (Business Logic เฉพาะ) |
| **ความซับซ้อน** | ง่าย (จัดการที่เดียว) | ยุ่งยาก (ต้องเขียนทุก Controller) |
| **แนะนำให้ใช้** | ✅ ใช้เป็นหลัก | ใช้เฉพาะกรณีที่ต้องการจัดการแตกต่างกัน |

### 2.3 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: จัดการ Exception เฉพาะ Controller (Quotation)

```java
@RestController
@RequestMapping("/api/v1/quotations")
public class QuotationController {

    private final QuotationService quotationService;

    // ✅ จัดการ Exception ที่เกิดจาก Quotation เฉพาะตัวนี้
    @ExceptionHandler(QuotationExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleQuotationExpired(QuotationExpiredException ex) {
        // ✅ ส่ง Response ที่มีข้อมูลเพิ่มเติม (แสดงเวลา expiry)
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .additionalInfo(Map.of(
                    "expiryDate", ex.getExpiryDate().toString(),
                    "suggestion", "Please create a new quotation"
                ))
                .build();
    }

    @ExceptionHandler(QuotationAlreadyApprovedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleQuotationAlreadyApproved(QuotationAlreadyApprovedException ex) {
        return ErrorResponse.builder()
                .message("Quotation already approved")
                .timestamp(new Date())
                .build();
    }

    @PutMapping("/{id}/approve")
    public QuotationResponseDTO approveQuotation(@PathVariable UUID id) {
        // ถ้าเกิด QuotationExpiredException หรือ QuotationAlreadyApprovedException
        // จะถูกจัดการโดยเมธอดด้านบน
        return quotationService.approveQuotation(id);
    }
}
```

#### ตัวอย่างที่ 2: ใช้ร่วมกับ GlobalExceptionHandler (การทำงานร่วมกัน)

```java
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    // ✅ Exception นี้จัดการใน Controller (เฉพาะตัว)
    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOutOfStock(OutOfStockException ex) {
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .additionalInfo(Map.of(
                    "availableStock", ex.getAvailableStock(),
                    "requestedQuantity", ex.getRequestedQuantity(),
                    "suggestion", "Reduce quantity or order more parts"
                ))
                .build();
    }

    // ❌ Exception อื่นๆ จะถูกส่งไปที่ GlobalExceptionHandler แทน
    @PostMapping("/issue")
    public InventoryResponseDTO issueGoods(@RequestBody InventoryIssueRequestDTO request) {
        return inventoryService.issueGoods(request);
    }
}
```

#### ตัวอย่างที่ 3: จัดการ MethodArgumentNotValidException (Validation) เฉพาะ Controller

```java
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    // ✅ จัดการ Validation Error ให้แตกต่างจาก Controller อื่น (ให้เป็นภาษาไทย)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        
        return ValidationErrorResponse.builder()
                .message("กรุณาตรวจสอบข้อมูลให้ถูกต้อง")
                .errors(errors)
                .timestamp(new Date())
                .build();
    }

    @PostMapping
    public CustomerResponseDTO createCustomer(@Valid @RequestBody CustomerCreateRequestDTO request) {
        return customerService.createCustomer(request);
    }
}
```

### 2.4 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Exception ซ้อนกัน** | ถ้ามีทั้งใน Controller และ Global สําหรับ Exception เดียวกัน → Controller จะถูกใช้ก่อน | ใช้ Controller Handler สำหรับกรณีพิเศษ, Global Handler สำหรับกรณีทั่วไป |
| **เขียนเยอะเกินไป** | ถ้าเขียน Exception Handler ทุก Controller จะซ้ำซ้อน | ใช้ GlobalExceptionHandler เป็นหลัก, เฉพาะที่ต่างกันถึงจะเขียนใน Controller |

---

## 3. @InitBinder (กำหนดรูปแบบการ Binding)

### 3.1 ความหมายและจุดประสงค์

**`@InitBinder`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้กำหนดค่าเริ่มต้น (Initialization) ให้กับ DataBinder ที่ใช้ใน Controller นี้"**

- ใช้กับ **เมธอด** ใน Controller (เมธอดนี้จะถูกเรียกก่อนทุก Request)
- ใช้สำหรับ:
  - กำหนดรูปแบบ **Date / DateTime** ที่ใช้แปลง String → Date
  - กำหนด **Validator** ที่ใช้ตรวจสอบข้อมูลเฉพาะ Controller
  - กำหนด **Property Editor** สำหรับการแปลงชนิดข้อมูล
  - กำหนด **WhiteList / BlackList** ของฟิลด์ที่รับได้

### 3.2 ทำงานอย่างไร?

1. เมื่อมี Request เข้ามา Spring จะสร้าง DataBinder สำหรับ Controller นั้น
2. Spring จะเรียกเมธอดที่มี `@InitBinder` (ถ้ามี)
3. เราสามารถ configure DataBinder ในเมธอดนั้นได้ (เช่น กำหนดรูปแบบวันที่, เพิ่ม Validator)

### 3.3 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: กำหนดรูปแบบวันที่ (Date / LocalDate)

```java
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    // ✅ กำหนดให้ Date รับรูปแบบ dd/MM/yyyy (หรือ yyyy-MM-dd)
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        
        // ✅ แปลง String → Date ด้วยรูปแบบที่กำหนด
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
        // ✅ สำหรับ LocalDate (Java 8) ต้องใช้วิธีอื่น
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text != null && !text.isEmpty()) {
                    setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });
    }

    // ✅ ตอนนี้ @RequestParam ที่เป็น Date จะแปลงจาก String ตามรูปแบบที่กำหนด
    @GetMapping("/daily")
    public ResponseEntity<DailyReportDTO> getDailyReport(
            @RequestParam("date") Date date) {  // ✅ ส่ง "25/12/2024" ได้
        return ResponseEntity.ok(reportService.getDailyReport(date));
    }
}
```

#### ตัวอย่างที่ 2: กำหนด Validator เฉพาะ Controller

```java
@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderValidator purchaseOrderValidator;

    // ✅ ตั้งค่า Validator ให้กับ Controller นี้
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // ✅ เพิ่ม Validator เฉพาะสำหรับ PO (ตรวจสอบ Supplier, Date, etc.)
        binder.addValidators(purchaseOrderValidator);
        
        // ✅ กำหนดฟิลด์ที่ไม่อนุญาตให้รับ (BlackList) - ป้องกันข้อมูลถูกแก้ไข
        binder.setDisallowedFields("id", "createdAt", "updatedAt");
    }

    @PostMapping
    public PurchaseOrderResponseDTO createPO(
            @Valid @RequestBody PurchaseOrderCreateRequestDTO request) {
        // ✅ request จะถูก validate ด้วย PurchaseOrderValidator ด้วย
        return purchaseOrderService.createPO(request);
    }
}
```

#### ตัวอย่างที่ 3: กำหนด WhiteList (ฟิลด์ที่อนุญาตเท่านั้น)

```java
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // ✅ อนุญาตเฉพาะฟิลด์เหล่านี้เท่านั้น (ฟิลด์อื่นจะถูก Ignore)
        binder.setAllowedFields(
            "fullName", 
            "phoneNumber", 
            "email", 
            "address", 
            "province", 
            "city"
        );
        // ❌ ฟิลด์เช่น id, createdAt, updatedAt จะไม่ถูกรับจาก Client
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO updateCustomer(
            @PathVariable UUID id,
            @RequestBody CustomerUpdateRequestDTO request) {
        // ✅ Client ส่งฟิลด์ id, createdAt, updatedAt มาจะถูกลบออกโดยอัตโนมัติ
        return customerService.updateCustomer(id, request);
    }
}
```

#### ตัวอย่างที่ 4: กำหนด Trim String (ลบช่องว่างหน้า-หลัง)

```java
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // ✅ ลบช่องว่างหน้า-หลังอัตโนมัติ (ทุก String Field)
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null) {
                    setValue(text.trim());
                }
            }
        });
    }

    @PostMapping
    public JobResponseDTO createJob(@RequestBody JobCreateRequestDTO request) {
        // ✅ "  Engine  " → "Engine" อัตโนมัติ
        return jobService.createJob(request);
    }
}
```

### 3.4 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **ใช้กับ @RequestBody** | `@InitBinder` ใช้กับ `@RequestParam` และ `@ModelAttribute` **ไม่ใช่** `@RequestBody` (เพราะ Jackson จัดการ `@RequestBody` โดยตรง) | ถ้าอยาก Trim String ใน `@RequestBody` ใช้ Custom Deserializer หรือ `@JsonCreator` แทน |
| **ลืม setLenient** | `SimpleDateFormat` ค่าเริ่มต้นเป็น lenient (ยอมรับวันที่ผิด) | ใช้ `dateFormat.setLenient(false)` เพื่อให้ Error เมื่อวันที่ผิด |
| **Override Global** | `@InitBinder` ใน Controller จะใช้แทน Global (ถ้ามี) | ใช้เฉพาะเมื่อต้องการต่างจาก Global |

---

## 4. @ModelAttribute (ผูกข้อมูลกับ Model / Before Advice)

### 4.1 ความหมายและจุดประสงค์

**`@ModelAttribute`** คือ Annotation ที่มี 2 หน้าที่หลัก:

1. **ใช้กับ Parameter**: ผูก Request Parameter หรือ Form Data เข้ากับ Object (คล้าย `@RequestBody` แต่ใช้กับ Form/Query)
2. **ใช้กับ Method**: ถูกเรียกก่อนทุก Request เพื่อเตรียมข้อมูลให้กับ Model (ใช้เหมือน "Before Advice")

### 4.2 ใช้กับ Parameter (ผูก Query Parameter / Form Data)

**`@ModelAttribute`** ใช้แทน `@RequestBody` เมื่อต้องการผูกข้อมูลจาก **Query String หรือ Form Data** (ไม่ใช่ JSON)

#### ตัวอย่าง: ค้นหาพร้อม Filter

```java
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    // ✅ ใช้ @ModelAttribute เพื่อรับ Filter จาก Query String
    @GetMapping
    public ResponseEntity<Page<JobResponseDTO>> listJobs(
            @ModelAttribute JobFilterDTO filter,
            Pageable pageable) {
        return ResponseEntity.ok(jobService.listJobs(filter, pageable));
    }
}

// DTO สำหรับ Filter
@Data
public class JobFilterDTO {
    private String status;        // ?status=IN_PROGRESS
    private String customerName;  // ?customerName=John
    private String startDate;     // ?startDate=2026-01-01
    private String endDate;       // ?endDate=2026-12-31
    private Boolean isUrgent;     // ?isUrgent=true
}
```

#### ตัวอย่าง: ใช้กับ Form (แบบเก่า)

```java
@Controller  // ❌ ใช้ @Controller (ไม่ใช่ @RestController) เพราะต้องส่ง View
public class LoginController {

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDTO loginRequest) {
        // ✅ รับค่า username, password จาก Form (application/x-www-form-urlencoded)
        return authService.login(loginRequest);
    }
}
```

### 4.3 ใช้กับ Method (Before Advice – เตรียมข้อมูล)

**`@ModelAttribute`** ที่ใช้กับ Method จะถูกเรียก **ก่อนทุก Request** ใน Controller เพื่อเตรียมข้อมูลให้กับ View (หรือเพิ่มข้อมูลใน Model)

#### ตัวอย่าง: เพิ่มข้อมูล Common สำหรับทุก API

```java
@RestController
@RequestMapping("/api/v1")
public class CommonDataController {

    // ✅ ถูกเรียกก่อนทุก Request ใน Controller นี้
    @ModelAttribute("appVersion")
    public String getAppVersion() {
        return "2.0.0";
    }

    @ModelAttribute("currentUser")
    public UserDTO getCurrentUser() {
        // ดึงข้อมูลผู้ใช้ปัจจุบันจาก SecurityContext
        return userService.getCurrentUser();
    }

    @ModelAttribute("supportedLanguages")
    public List<String> getSupportedLanguages() {
        return Arrays.asList("th", "en", "zh", "ja", "ko");
    }

    // ✅ เมธอดต่างๆ จะสามารถเข้าถึงข้อมูลเหล่านี้ได้ (ถ้าเป็น MVC)
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // model มี "appVersion", "currentUser", "supportedLanguages" อัตโนมัติ
        return "dashboard";
    }
}
```

#### ตัวอย่าง: ใช้กับ REST API (เพิ่มข้อมูลใน Response)

```java
@RestController
@RequestMapping("/api/v1/quotations")
public class QuotationController {

    // ✅ ถูกเรียกก่อนทุก Request
    @ModelAttribute("exchangeRate")
    public BigDecimal getExchangeRate() {
        return currencyService.getCurrentExchangeRate();
    }

    @GetMapping("/{id}")
    public QuotationResponseDTO getQuotation(@PathVariable UUID id) {
        // ไม่ต้องส่ง exchangeRate ใน DTO ก็ได้ เพราะ @ModelAttribute จัดการให้
        QuotationResponseDTO response = quotationService.getQuotation(id);
        response.setExchangeRate(getExchangeRate());  // ✅ ใช้ค่าเดียวกันทุก Request
        return response;
    }
}
```

#### ตัวอย่าง: ใช้สำหรับการ Validation / Pre-processing

```java
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    // ✅ ใช้ @ModelAttribute เพื่อตรวจสอบ Token ก่อนทุก Request
    @ModelAttribute
    public void validateToken(@RequestHeader("Authorization") String token) {
        if (!jwtService.validateToken(token)) {
            throw new UnauthorizedException("Invalid token");
        }
    }

    @PostMapping
    public PaymentResponseDTO recordPayment(@RequestBody PaymentRecordRequestDTO request) {
        // ✅ token จะถูกตรวจสอบก่อนทุกครั้ง
        return paymentService.recordPayment(request);
    }
}
```

### 4.4 เปรียบเทียบ @ModelAttribute vs @RequestBody

| คุณสมบัติ | `@ModelAttribute` | `@RequestBody` |
|-----------|-------------------|----------------|
| **Content-Type** | `application/x-www-form-urlencoded`, `multipart/form-data` | `application/json`, `application/xml` |
| **ใช้กับ** | Query String, Form Data, Path Variable | JSON Body, XML Body |
| **Binding** | ผูกข้อมูลจากหลายแหล่ง (Query + Form + Path) | ผูกจาก Body เท่านั้น |
| **Validation** | รองรับ `@Valid` (ใช้ `@InitBinder` เพื่อตั้งค่า) | รองรับ `@Valid` (ใช้ `@ControllerAdvice` เพื่อจัดการ) |
| **ใช้ใน REST** | ไม่ค่อยใช้ (ใช้ `@RequestParam` + DTO แทน) | ✅ ใช้หลัก |

---

## 5. สรุปตารางเปรียบเทียบ Annotation

| Annotation | ใช้ที่ไหน | หน้าที่ | ตัวอย่าง |
|-----------|---------|--------|----------|
| **`@ResponseStatus`** | Class / Method | กำหนด HTTP Status Code ที่จะส่งกลับ | `@ResponseStatus(HttpStatus.CREATED)` |
| **`@ExceptionHandler`** | Method (ใน Controller) | จัดการ Exception เฉพาะ Controller | `@ExceptionHandler(ResourceNotFoundException.class)` |
| **`@InitBinder`** | Method | กำหนดรูปแบบการแปลงข้อมูล (Date, Trim) | `@InitBinder public void initBinder(WebDataBinder binder)` |
| **`@ModelAttribute`** | Method / Parameter | ผูกข้อมูลจาก Query/Form (หรือเตรียมข้อมูลก่อน Request) | `@ModelAttribute JobFilterDTO filter` |

---

## 6. ตัวอย่าง Controller ที่สมบูรณ์ (ใช้ Annotation ทั้งหมด)

```java
package com.template.app.modules.job.presentation.controller;

import com.template.app.exception.models.ResourceNotFoundException;
import com.template.app.modules.job.application.interfaces.JobService;
import com.template.app.modules.job.presentation.dto.request.JobCreateRequestDTO;
import com.template.app.modules.job.presentation.dto.request.JobFilterDTO;
import com.template.app.modules.job.presentation.dto.response.JobResponseDTO;
import com.template.app.modules.job.presentation.validator.JobValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final JobValidator jobValidator;

    // ✅ 1. @InitBinder: กำหนดรูปแบบ Date + Validator
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // กำหนดรูปแบบ Date
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null && !text.isEmpty()) {
                    setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
            }
        });

        // เพิ่ม Validator เฉพาะ
        binder.addValidators(jobValidator);

        // ไม่อนุญาตให้รับฟิลด์เหล่านี้
        binder.setDisallowedFields("id", "createdAt", "updatedAt");
    }

    // ✅ 2. @ModelAttribute: เตรียมข้อมูลก่อน Request
    @ModelAttribute("defaultStatus")
    public String getDefaultStatus() {
        return "OPEN";
    }

    // ✅ 3. @GetMapping: ใช้ @ModelAttribute สำหรับ Filter
    @GetMapping
    public ResponseEntity<Page<JobResponseDTO>> listJobs(
            @ModelAttribute JobFilterDTO filter,
            Pageable pageable) {
        // ใช้ filter ในการค้นหา
        return ResponseEntity.ok(jobService.listJobs(filter, pageable));
    }

    // ✅ 4. @PostMapping: ใช้ @RequestBody + @Valid
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // ✅ 5. @ResponseStatus: 201 Created
    public JobResponseDTO createJob(@Valid @RequestBody JobCreateRequestDTO request) {
        return jobService.createJob(request);
    }

    // ✅ 6. @GetMapping: ใช้ @PathVariable
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJob(@PathVariable UUID id) {
        return ResponseEntity.ok(jobService.getJob(id));
    }

    // ✅ 7. @ExceptionHandler: จัดการ Exception เฉพาะ Controller
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return ErrorResponse.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }

    // ✅ 8. @PutMapping: อัปเดต
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDTO> updateJob(
            @PathVariable UUID id,
            @Valid @RequestBody JobUpdateRequestDTO request) {
        return ResponseEntity.ok(jobService.updateJob(id, request));
    }

    // ✅ 9. @DeleteMapping: ลบ
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // ✅ 204 No Content
    public void deleteJob(@PathVariable UUID id) {
        jobService.deleteJob(id);
    }
}
```

---

## 7. คำแนะนำสำหรับโปรเจกต์ Auto Repair Shop

1. **`@ResponseStatus`**:
   - ใช้กับ **Exception Class** เป็นหลัก (เช่น `ResourceNotFoundException`, `DuplicateResourceException`)
   - ใช้กับ Controller Method เฉพาะกรณีที่ต้องการ Status Code ที่ไม่ใช่ 200 (เช่น `@PostMapping` + `@ResponseStatus(HttpStatus.CREATED)`)

2. **`@ExceptionHandler` (ใน Controller)**:
   - ใช้เฉพาะกรณีที่ต้องการจัดการ Exception **แตกต่าง** จาก Global Handler (เช่น แสดงข้อมูลเพิ่มเติม)
   - โดยทั่วไป **ควรใช้ `@ControllerAdvice` (Global)** แทน เพื่อไม่ให้โค้ดซ้ำ

3. **`@InitBinder`**:
   - ใช้สำหรับกำหนดรูปแบบ **Date** ที่แตกต่างกันในแต่ละ Controller
   - ใช้กำหนด **WhiteList / BlackList** ของฟิลด์เพื่อป้องกันข้อมูลถูกแก้ไข
   - ใช้เพิ่ม **Validator** เฉพาะ Controller (ถ้าต้องการ)
   - ระวัง: `@InitBinder` ใช้ไม่ได้กับ `@RequestBody` (ใช้ Jackson แทน)

4. **`@ModelAttribute`**:
   - ใช้กับ **Method**: สำหรับเตรียมข้อมูล Common ให้กับทุก Request ใน Controller (เช่น ข้อมูลผู้ใช้, ภาษา, อัตราแลกเปลี่ยน)
   - ใช้กับ **Parameter**: สำหรับรับ Query Parameter หลายตัวเป็น DTO (ใช้กับ `@GetMapping` แทนที่จะเขียน `@RequestParam` ทีละตัว)
   - ใน REST API มักใช้ `@ModelAttribute` สำหรับ Filter / Search มากกว่า Form

---
# อธิบาย Web Layer Annotation (หัวข้อขั้นสูง) แบบละเอียด

ต่อเนื่องจาก `@RestController`, `@RequestMapping`, `@RequestBody`, `@InitBinder`, `@ExceptionHandler` และ `@ModelAttribute` ที่เราได้เรียนรู้ไปแล้ว ยังมี Annotation อีก 4 ตัวที่สำคัญสำหรับการจัดการข้อมูลประเภทพิเศษใน Web Layer:

- **`@RequestPart`** – สำหรับรับ Multipart File (อัปโหลดไฟล์) พร้อม JSON Metadata
- **`@SessionAttribute` / `@SessionAttributes`** – การจัดการข้อมูลใน HTTP Session
- **`@CookieValue`** – การอ่านค่าจาก Cookie ของ Browser
- **`@RequestHeader`** – การอ่านค่าจาก HTTP Headers (เช่น Authorization Token, User-Agent)

เรามาทำความเข้าใจแบบเจาะลึกพร้อมตัวอย่างการใช้งานในระบบ Auto Repair Shop ของเรากันครับ

---

## 1. @RequestPart (Multipart File Upload)

### 1.1 ความหมายและจุดประสงค์

**`@RequestPart`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้ผูกข้อมูลจาก Multipart Request (ไฟล์หรือ JSON) เข้ากับ Parameter"**

- ใช้กับ **Parameter** ของเมธอด Controller
- ใช้กับ `multipart/form-data` (การอัปโหลดไฟล์)
- สามารถรับทั้ง **ไฟล์ (`MultipartFile`)** และ **JSON (`@RequestPart` + DTO)** ใน Request เดียวกัน

### 1.2 ต่างจาก @RequestParam อย่างไร?

| Annotation | ใช้กับ | ความสามารถ |
|-----------|-------|-----------|
| **`@RequestParam`** | Form Field ทั่วไป (String, Integer) | รับค่า Form Field ทั่วไป (ไม่ใช่ไฟล์) |
| **`@RequestPart`** | Multipart (ไฟล์ + JSON) | รับไฟล์ (`MultipartFile`) และ JSON (`application/json`) |

### 1.3 ทำงานอย่างไร?

1. Client ส่ง Request ด้วย `Content-Type: multipart/form-data`
2. Spring ใช้ `MultipartResolver` เพื่อแยกส่วนต่างๆ ของ Request
3. แต่ละส่วน (Part) จะถูกผูกกับ Parameter โดยใช้ชื่อที่ระบุใน `@RequestPart`

### 1.4 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: อัปโหลดรูปอะไหล่ (ไฟล์เดียว)

```java
package com.template.app.modules.inventory.presentation.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/parts")
public class PartMasterController {

    /*
        ฟังก์ชันนี้ใช้อัปโหลดรูปภาพสำหรับอะไหล่
        ฟังก์ชันนี้จะอัปโหลดไฟล์รูปภาพและอัปเดต URL รูปให้กับอะไหล่
        Function: Uploads an image file for a part and updates the part's image URL.
    */
    @PostMapping("/{partId}/image")
    public ResponseEntity<String> uploadPartImage(
            @PathVariable UUID partId,
            @RequestPart("file") MultipartFile file) {  // ✅ รับไฟล์จากฟอร์ม
        // ตรวจสอบไฟล์
        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }
        
        // บันทึกไฟล์ลง S3 หรือ Local Storage
        String imageUrl = fileStorageService.storeFile(file, "parts/" + partId);
        
        // อัปเดต URL รูปในฐานข้อมูล
        partMasterService.updateImageUrl(partId, imageUrl);
        
        return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);
    }
}
```

#### ตัวอย่างที่ 2: อัปโหลดไฟล์พร้อม Metadata (JSON)

```java
package com.template.app.modules.document.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    /*
        ฟังก์ชันนี้ใช้อัปโหลดเอกสารพร้อมข้อมูล Metadata (JSON)
        ฟังก์ชันนี้จะรับทั้งไฟล์และข้อมูล JSON ใน Request เดียวกัน
        Function: Uploads a document file along with JSON metadata in one request.
    */
    @PostMapping("/upload-with-metadata")
    public ResponseEntity<DocumentResponseDTO> uploadDocumentWithMetadata(
            @RequestPart("file") MultipartFile file,                  // ✅ ไฟล์
            @RequestPart("metadata") DocumentMetadataDTO metadata) {  // ✅ JSON Metadata
        // ตรวจสอบไฟล์
        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }
        
        // บันทึกไฟล์
        String filePath = fileStorageService.storeFile(file, "documents");
        
        // สร้างเอกสารด้วยข้อมูลจาก Metadata
        DocumentResponseDTO response = documentService.createDocument(
            filePath, 
            file.getOriginalFilename(), 
            metadata
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

// DTO สำหรับ Metadata
@Data
public class DocumentMetadataDTO {
    private String documentType;    // INVOICE, QUOTATION, RECEIPT
    private String referenceType;   // JOB, QUOTATION, INVOICE
    private UUID referenceId;
    private String description;
    private List<String> tags;
}
```

#### ตัวอย่างที่ 3: หลายไฟล์พร้อมกัน (Bulk Upload)

```java
@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    /*
        ฟังก์ชันนี้อัปโหลดหลายไฟล์พร้อมกัน (เช่น รูปภาพหลายรูปของ Job)
        ฟังก์ชันนี้จะรับ List ของไฟล์และอัปโหลดทั้งหมด
        Function: Uploads multiple files at once (e.g., multiple images for a Job).
    */
    @PostMapping("/bulk-upload")
    public ResponseEntity<List<DocumentResponseDTO>> bulkUploadDocuments(
            @RequestParam("files") MultipartFile[] files,  // ✅ Array ของไฟล์
            @RequestParam("referenceType") String referenceType,
            @RequestParam("referenceId") UUID referenceId) {
        
        List<DocumentResponseDTO> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            // อัปโหลดทีละไฟล์
            String filePath = fileStorageService.storeFile(file, "documents/" + referenceType);
            DocumentResponseDTO response = documentService.createDocument(
                filePath, 
                file.getOriginalFilename(), 
                referenceType, 
                referenceId
            );
            responses.add(response);
        }
        
        return ResponseEntity.ok(responses);
    }
}
```

#### ตัวอย่างที่ 4: ใช้ @RequestPart สำหรับ JSON (กรณีที่ Content-Type ถูกต้อง)

```java
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    /*
        ฟังก์ชันนี้สร้าง Job พร้อมอัปโหลดไฟล์แนบ (ถ้ามี)
        ฟังก์ชันนี้จะรับ JSON Job data และไฟล์แนบพร้อมกัน
        Function: Creates a Job with optional attachment upload.
    */
    @PostMapping(value = "/create-with-attachment", 
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JobResponseDTO> createJobWithAttachment(
            @RequestPart("jobData") JobCreateRequestDTO jobRequest,  // ✅ JSON Part
            @RequestPart(value = "attachment", required = false) MultipartFile attachment) {
        // ถ้ามีไฟล์แนบ ให้บันทึกไว้
        if (attachment != null && !attachment.isEmpty()) {
            String filePath = fileStorageService.storeFile(attachment, "jobs/attachments");
            jobRequest.setAttachmentPath(filePath);
        }
        
        JobResponseDTO response = jobService.createJob(jobRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

### 1.5 Request ที่ Client ส่ง (cURL)
```bash
curl -X POST http://localhost:1080/api/v1/documents/upload-with-metadata \
  -F "file=@/path/to/invoice.pdf" \
  -F "metadata={\"documentType\":\"INVOICE\",\"referenceType\":\"JOB\",\"referenceId\":\"123e4567...\"};type=application/json"
```

### 1.6 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **ขนาดไฟล์เกิน** | Default limit = 1MB | ตั้งค่าใน `application.yml`: `spring.servlet.multipart.max-file-size=10MB` |
| **ชื่อ Part ไม่ตรง** | ต้องตรงกับชื่อใน `@RequestPart` | ตรวจสอบ `name` ของ Input ใน Form หรือ key ใน JSON |
| **ใช้กับ JSON** | ถ้าใช้ `@RequestPart("metadata") MetadataDTO` ต้องมี `Content-Type: application/json` ใน Part นั้น | Client ต้องตั้งค่า `type=application/json` ใน Part |

---

## 2. @SessionAttribute และ @SessionAttributes (การจัดการ Session)

### 2.1 ความหมายและจุดประสงค์

**`@SessionAttributes`** และ **`@SessionAttribute`** คือ Annotation ที่ใช้จัดการข้อมูลใน **HTTP Session**

| Annotation | ใช้ที่ไหน | หน้าที่ |
|-----------|---------|---------|
| **`@SessionAttributes`** | **Class-Level** (Controller) | ประกาศว่า Model Attribute ตัวไหนที่จะถูกเก็บไว้ใน Session (ใช้ร่วมกับ `@ModelAttribute`) |
| **`@SessionAttribute`** | **Parameter** (Method) | ดึงค่า Attribute ที่ระบุจาก Session โดยตรง (เหมือน `session.getAttribute()`) |

### 2.2 ทำงานอย่างไร?

1. **`@SessionAttributes`**: เมื่อเราใช้ `@ModelAttribute` ที่มีชื่ออยู่ใน `@SessionAttributes` Spring จะเก็บ Object นั้นไว้ใน Session
2. **`@SessionAttribute`**: ดึง Object จาก Session มาใช้โดยตรง (ไม่ต้องเรียก `HttpSession` ด้วยตัวเอง)

### 2.3 ตัวอย่างในระบบของเรา (Shopping Cart)

#### ตัวอย่างที่ 1: ใช้ @SessionAttributes เพื่อเก็บ Cart

```java
package com.template.app.modules.weborder.presentation.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@RestController
@RequestMapping("/api/v1/wos/cart")
@SessionAttributes("cart")  // ✅ ประกาศว่า "cart" จะถูกเก็บใน Session
public class CartController {

    /*
        ฟังก์ชันนี้สร้างตะกร้าสินค้าใหม่ และเก็บไว้ใน Session
        ฟังก์ชันนี้จะสร้าง ShoppingCart และเก็บไว้ใน Session โดยอัตโนมัติ
        Function: Creates a new shopping cart and stores it in the session.
    */
    @ModelAttribute("cart")
    public ShoppingCart createCart() {
        // ✅ @ModelAttribute นี้จะถูกเรียกเมื่อยังไม่มี "cart" ใน Session
        // เมื่อมีแล้ว จะไม่ถูกเรียกอีก (ใช้ค่าจาก Session แทน)
        return new ShoppingCart();
    }

    /*
        ฟังก์ชันนี้เพิ่มสินค้าในตะกร้า (ตะกร้าอยู่ใน Session)
        ฟังก์ชันนี้จะดึง cart จาก Session โดยอัตโนมัติ
        Function: Adds an item to the session-stored shopping cart.
    */
    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(
            @ModelAttribute("cart") ShoppingCart cart,  // ✅ ดึงจาก Session
            @RequestBody AddToCartRequestDTO request) {
        cart.addItem(request.getItemId(), request.getQuantity());
        return ResponseEntity.ok(CartResponseDTO.from(cart));
    }

    /*
        ฟังก์ชันนี้ดูตะกร้าสินค้าปัจจุบัน
        ฟังก์ชันนี้จะดึง cart จาก Session
        Function: Retrieves the current shopping cart from session.
    */
    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(
            @ModelAttribute("cart") ShoppingCart cart) {
        return ResponseEntity.ok(CartResponseDTO.from(cart));
    }

    /*
        ฟังก์ชันนี้ล้างตะกร้า (เคลียร์ Session)
        ฟังก์ชันนี้จะลบ "cart" ออกจาก Session
        Function: Clears the shopping cart from the session.
    */
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(SessionStatus sessionStatus) {
        // ✅ ลบ Attribute "cart" ออกจาก Session
        sessionStatus.setComplete();  // ลบ @SessionAttributes ทั้งหมดที่ประกาศไว้
        return ResponseEntity.noContent().build();
    }
}
```

#### ตัวอย่างที่ 2: ใช้ @SessionAttribute (ดึงค่าโดยตรง)

```java
@RestController
@RequestMapping("/api/v1/wos/checkout")
public class CheckoutController {

    /*
        ฟังก์ชันนี้ใช้ @SessionAttribute เพื่อดึง cart จาก Session โดยตรง
        ฟังก์ชันนี้จะดึง "cart" จาก Session แบบตรงๆ (ถ้าไม่มีจะ error)
        Function: Retrieves the cart directly from the session using @SessionAttribute.
    */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> checkout(
            @SessionAttribute("cart") ShoppingCart cart) {  // ✅ ดึงจาก Session โดยตรง
        if (cart.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }
        OrderResponseDTO order = orderService.createOrderFromCart(cart);
        return ResponseEntity.ok(order);
    }
}
```

#### ตัวอย่างที่ 3: ใช้ @SessionAttribute(required = false) (ถ้าไม่มีให้เป็น null)

```java
@RestController
@RequestMapping("/api/v1/wos/cart")
public class CartController {

    /*
        ฟังก์ชันนี้ตรวจสอบว่ามีตะกร้าหรือไม่ (ใช้ required = false)
        ฟังก์ชันนี้จะคืนค่า cart ถ้ามี หรือ null ถ้าไม่มี
        Function: Checks if a cart exists (returns null if not found).
    */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> cartExists(
            @SessionAttribute(value = "cart", required = false) ShoppingCart cart) {
        return ResponseEntity.ok(cart != null && !cart.isEmpty());
    }
}
```

### 2.4 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **ไม่เหมาะกับ REST API (Stateless)** | REST API ควรเป็น Stateless (ไม่ใช้ Session) | ในระบบ Microservices ควรใช้ **JWT Token** แทน Session |
| **Session กระจายไม่ได้** | ถ้ารันหลายเครื่อง Session จะไม่ถูกแชร์ | ใช้ **Redis** เป็น Session Storage (Spring Session) |
| **ลืม clear Session** | ข้อมูลค้างใน Session ตลอดไป | ใช้ `SessionStatus.setComplete()` หรือ `session.removeAttribute("key")` |
| **ใช้กับ @RestController** | `@SessionAttributes` ใช้ได้ แต่ไม่แนะนำเพราะ REST ควร Stateless | ใช้ Redis Cache หรือ Database แทน |

---

## 3. @CookieValue (อ่านค่าจาก Cookie)

### 3.1 ความหมายและจุดประสงค์

**`@CookieValue`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้ดึงค่าจาก Cookie ที่ระบุใน HTTP Request"**

- ใช้กับ **Parameter** ของเมธอด Controller
- เหมาะสำหรับการอ่านค่า เช่น **JSESSIONID**, **Language Preference**, **Tracking ID**

### 3.2 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: อ่านค่า Language Preference จาก Cookie

```java
@RestController
@RequestMapping("/api/v1/languages")
public class LanguageController {

    /*
        ฟังก์ชันนี้อ่านค่าภาษาจาก Cookie (ถ้ามี)
        ฟังก์ชันนี้จะอ่าน Cookie "lang" เพื่อกำหนดภาษาที่ผู้ใช้เลือก
        Function: Reads the language preference from the "lang" cookie.
    */
    @GetMapping("/preference")
    public ResponseEntity<String> getLanguagePreference(
            @CookieValue(value = "lang", defaultValue = "th") String language) {
        // ✅ ถ้าไม่มี Cookie "lang" ให้ใช้ค่า default "th"
        return ResponseEntity.ok("Current language: " + language);
    }

    /*
        ฟังก์ชันนี้ตั้งค่าภาษาและเก็บไว้ใน Cookie
        ฟังก์ชันนี้จะสร้าง Cookie "lang" และส่งกลับไปให้ Browser เก็บไว้
        Function: Sets the language preference and stores it in a cookie.
    */
    @PostMapping("/set")
    public ResponseEntity<Void> setLanguagePreference(
            @RequestParam String language,
            HttpServletResponse response) {
        // สร้าง Cookie ชื่อ "lang" เก็บเป็นเวลา 30 วัน
        Cookie cookie = new Cookie("lang", language);
        cookie.setMaxAge(30 * 24 * 60 * 60);  // 30 วัน
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
```

#### ตัวอย่างที่ 2: ตรวจสอบ Tracking ID (สำหรับ Analytics)

```java
@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    /*
        ฟังก์ชันนี้อ่าน Tracking ID จาก Cookie เพื่อติดตามผู้ใช้
        ฟังก์ชันนี้จะอ่าน Cookie "_tracking_id" หรือสร้างใหม่ถ้าไม่มี
        Function: Reads the tracking ID from cookie for user analytics.
    */
    @GetMapping("/track")
    public ResponseEntity<String> trackUser(
            @CookieValue(value = "_tracking_id", required = false) String trackingId,
            HttpServletResponse response) {
        if (trackingId == null) {
            // ถ้าไม่มี Tracking ID ให้สร้างใหม่
            trackingId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("_tracking_id", trackingId);
            cookie.setMaxAge(365 * 24 * 60 * 60);  // 1 ปี
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(trackingId);
    }
}
```

#### ตัวอย่างที่ 3: ใช้ @CookieValue กับ HttpOnly Cookie (Security)

```java
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    /*
        ฟังก์ชันนี้ตรวจสอบ Refresh Token จาก Cookie (HttpOnly)
        ฟังก์ชันนี้จะอ่าน Cookie "refresh_token" ที่เป็น HttpOnly เพื่อความปลอดภัย
        Function: Reads the refresh token from an HttpOnly cookie for security.
    */
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(
            @CookieValue(value = "refresh_token", required = true) String refreshToken) {
        // ✅ ใช้ Refresh Token เพื่อสร้าง Access Token ใหม่
        LoginResponseDTO response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}
```

### 3.3 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Cookie ถูก Block** | Browser อาจ Block Cookie (Third-party, Incognito) | ใช้ `sameSite=None; Secure` หรือใช้ Authorization Header แทน |
| **Security (XSS)** | ถ้า Cookie ไม่มี HttpOnly อาจถูก Steal โดย JavaScript | ใช้ `cookie.setHttpOnly(true)` สำหรับ Token |
| **Security (CSRF)** | Cookie อาจถูกใช้โจมตี CSRF | ใช้ `sameSite=Strict` หรือใช้ CSRF Token เพิ่ม |
| **ขนาด Cookie** | Cookie มีขนาดจำกัด (4KB) | ไม่ควรเก็บข้อมูลขนาดใหญ่ใน Cookie |

---

## 4. @RequestHeader (อ่านค่าจาก HTTP Headers)

### 4.1 ความหมายและจุดประสงค์

**`@RequestHeader`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้ดึงค่าจาก HTTP Header ที่ระบุใน Request"**

- ใช้กับ **Parameter** ของเมธอด Controller
- ใช้บ่อยที่สุดสำหรับการอ่าน **Authorization Token (JWT)**, **User-Agent**, **Content-Type**, **Accept-Language**

### 4.2 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: อ่าน Authorization Token (JWT)

```java
@RestController
@RequestMapping("/api/v1/protected")
public class ProtectedController {

    /*
        ฟังก์ชันนี้อ่าน Authorization Header เพื่อตรวจสอบ JWT Token
        ฟังก์ชันนี้จะดึง Token จาก Header "Authorization" และตรวจสอบความถูกต้อง
        Function: Reads the Authorization header to validate the JWT token.
    */
    @GetMapping("/data")
    public ResponseEntity<String> getProtectedData(
            @RequestHeader("Authorization") String authHeader) {
        // authHeader = "Bearer eyJhbGciOiJIUzI1NiIs..."
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Invalid Authorization header");
        }
        String token = authHeader.substring(7);  // ตัด "Bearer "
        if (!jwtService.validateToken(token)) {
            throw new UnauthorizedException("Invalid token");
        }
        return ResponseEntity.ok("Protected data");
    }
}
```

#### ตัวอย่างที่ 2: อ่าน User-Agent (ข้อมูล Browser/Device)

```java
@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    /*
        ฟังก์ชันนี้อ่าน User-Agent เพื่อบันทึกข้อมูลอุปกรณ์ที่ใช้งาน
        ฟังก์ชันนี้จะบันทึกประเภท Device, Browser, OS จาก User-Agent Header
        Function: Reads the User-Agent header to log device information.
    */
    @PostMapping("/log-visit")
    public ResponseEntity<Void> logVisit(
            @RequestHeader("User-Agent") String userAgent) {
        // วิเคราะห์ User-Agent
        String device = parseDevice(userAgent);   // "Mobile", "Desktop", "Tablet"
        String browser = parseBrowser(userAgent); // "Chrome", "Safari", "Firefox"
        String os = parseOS(userAgent);           // "Windows", "iOS", "Android"
        
        analyticsService.logVisit(device, browser, os);
        return ResponseEntity.ok().build();
    }
}
```

#### ตัวอย่างที่ 3: อ่านหลาย Header พร้อมกัน

```java
@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    /*
        ฟังก์ชันนี้ใช้ @RequestHeader หลายตัวเพื่อตรวจสอบ Context
        ฟังก์ชันนี้จะอ่าน Content-Type, Accept-Language, และ X-Request-ID
        Function: Reads multiple headers to determine request context.
    */
    @PostMapping("/send")
    public ResponseEntity<Void> sendEmail(
            @RequestBody EmailRequestDTO request,
            @RequestHeader(value = "Content-Type", defaultValue = "application/json") String contentType,
            @RequestHeader(value = "Accept-Language", defaultValue = "th") String language,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        System.out.println("Content-Type: " + contentType);
        System.out.println("Language: " + language);
        System.out.println("Request ID: " + requestId);
        
        // ส่งอีเมลตามภาษา
        emailService.sendEmail(request, language);
        return ResponseEntity.ok().build();
    }
}
```

#### ตัวอย่างที่ 4: ใช้ @RequestHeader กับ Map (รับทุก Header)

```java
@RestController
@RequestMapping("/api/v1/debug")
public class DebugController {

    /*
        ฟังก์ชันนี้ใช้ Map<String, String> เพื่อรับทุก Header
        ฟังก์ชันนี้จะแสดง Headers ทั้งหมดที่ client ส่งมา
        Function: Uses Map to receive all headers for debugging purposes.
    */
    @GetMapping("/headers")
    public ResponseEntity<Map<String, String>> getAllHeaders(
            @RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok(headers);
    }

    /*
        ฟังก์ชันนี้ใช้ HttpHeaders (Spring Object) เพื่อรับทุก Header
        ฟังก์ชันนี้จะแสดง Headers ทั้งหมดที่ client ส่งมา (แบบ Object)
        Function: Uses HttpHeaders object to receive all headers.
    */
    @GetMapping("/headers-obj")
    public ResponseEntity<HttpHeaders> getAllHeadersObj(
            @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(headers);
    }
}
```

#### ตัวอย่างที่ 5: ใช้กับ Feign Client (Microservices)

```java
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    /*
        ฟังก์ชันนี้ส่งต่อ Authorization Header ไปยัง Service อื่น
        ฟังก์ชันนี้จะอ่าน Token และส่งต่อไปยัง Inventory Service
        Function: Forwards the Authorization header to another microservice.
    */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestBody OrderCreateRequestDTO request,
            @RequestHeader("Authorization") String authToken) {
        // ส่ง Authorization Token ไปยัง Inventory Service (ผ่าน Feign Client)
        InventoryCheckDTO inventoryCheck = inventoryClient.checkStock(
            request.getItems(), 
            authToken  // ✅ ส่ง Token ต่อไป
        );
        
        if (!inventoryCheck.isAvailable()) {
            throw new OutOfStockException("Some items are out of stock");
        }
        
        OrderResponseDTO order = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
```

### 4.3 ข้อควรระวัง

| ปัญหา | คำอธิบาย | วิธีแก้ไข |
|-------|----------|----------|
| **Header ไม่มี (Required)** | ถ้า `required = true` (default) และไม่มี Header → Error | ใช้ `required = false` หรือ `defaultValue` |
| **Authorization Format** | Client ส่ง "Bearer token" หรือ "Basic base64" | ตรวจสอบ Format และดึงเฉพาะ Token |
| **Case Sensitivity** | Header Name ไม่ Case-sensitive (Spring จัดการให้) | เขียนยังไงก็ได้ `"authorization"` หรือ `"Authorization"` |
| **Header Injection** | Client อาจส่ง Header ปลอม | ตรวจสอบและ Validate ทุกครั้ง โดยเฉพาะ Authorization |

---

## 5. สรุปตารางเปรียบเทียบ Annotation

| Annotation | ใช้กับ | หน้าที่ | Content-Type | ตัวอย่าง |
|-----------|-------|--------|--------------|----------|
| **`@RequestPart`** | Parameter | รับ Multipart (ไฟล์ + JSON) | `multipart/form-data` | `@RequestPart("file") MultipartFile file` |
| **`@SessionAttributes`** | Class | ประกาศ Attribute ที่เก็บใน Session | - | `@SessionAttributes("cart")` |
| **`@SessionAttribute`** | Parameter | ดึงค่า Attribute จาก Session | - | `@SessionAttribute("cart") ShoppingCart cart` |
| **`@CookieValue`** | Parameter | อ่านค่าจาก Cookie | - | `@CookieValue("lang") String lang` |
| **`@RequestHeader`** | Parameter | อ่านค่าจาก HTTP Header | - | `@RequestHeader("Authorization") String token` |

---

## 6. ตัวอย่าง Controller ที่สมบูรณ์ (ใช้ Annotation ทั้งหมด)

```java
package com.template.app.modules.weborder.presentation.controller;

import com.template.app.modules.weborder.domain.ShoppingCart;
import com.template.app.modules.weborder.presentation.dto.request.AddToCartRequestDTO;
import com.template.app.modules.weborder.presentation.dto.request.OrderCreateRequestDTO;
import com.template.app.modules.weborder.presentation.dto.response.CartResponseDTO;
import com.template.app.modules.weborder.presentation.dto.response.OrderResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wos")
@SessionAttributes("cart")
@RequiredArgsConstructor
public class WebOrderController {

    private final OrderService orderService;
    private final FileStorageService fileStorageService;

    // ========================================================================
    // 1. @ModelAttribute: สร้าง Cart เริ่มต้น (เก็บใน Session)
    // ========================================================================
    @ModelAttribute("cart")
    public ShoppingCart createCart() {
        return new ShoppingCart();
    }

    // ========================================================================
    // 2. @CookieValue: อ่านภาษาและ Tracking ID
    // ========================================================================
    @GetMapping("/preference")
    public ResponseEntity<String> getPreferences(
            @CookieValue(value = "lang", defaultValue = "th") String language,
            @CookieValue(value = "_tracking_id", required = false) String trackingId) {
        return ResponseEntity.ok("Language: " + language + ", Tracking: " + trackingId);
    }

    // ========================================================================
    // 3. @RequestHeader: อ่าน Authorization และ User-Agent
    // ========================================================================
    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody OrderCreateRequestDTO request,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader(value = "User-Agent", required = false) String userAgent,
            @SessionAttribute("cart") ShoppingCart cart) {  // ✅ 4. @SessionAttribute
        
        // ตรวจสอบ Token
        if (!jwtService.validateToken(authToken)) {
            throw new UnauthorizedException("Invalid token");
        }
        
        // สร้าง Order
        OrderResponseDTO order = orderService.createOrderFromCart(cart, request);
        
        // เคลียร์ Cart
        cart.clear();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // ========================================================================
    // 5. @RequestPart: อัปโหลดไฟล์พร้อม Metadata
    // ========================================================================
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponseDTO> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @RequestPart("metadata") DocumentMetadataDTO metadata,
            @RequestHeader("Authorization") String authToken) {
        
        // บันทึกไฟล์
        String filePath = fileStorageService.storeFile(file, "orders/attachments");
        
        // สร้าง Document
        DocumentResponseDTO response = documentService.createDocument(
            filePath, file.getOriginalFilename(), metadata, authToken
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

---

## 7. คำแนะนำสำหรับโปรเจกต์ Auto Repair Shop

1. **`@RequestPart`**: 
   - ใช้สำหรับอัปโหลดรูปอะไหล่ (`/api/v1/parts/{id}/image`)
   - ใช้สำหรับอัปโหลดเอกสารพร้อม Metadata (`/api/v1/documents/upload`)
   - ใช้สำหรับอัปโหลดรูป Job Card (`/api/v1/jobs/{id}/images`)

2. **`@SessionAttribute` / `@SessionAttributes`**: 
   - **ไม่แนะนำ** สำหรับ REST API (ควรใช้ JWT + Redis แทน)
   - ถ้าต้องการใช้จริง (Web MVC แบบเก่า) ให้ใช้กับ Shopping Cart หรือ User Session
   - ต้องใช้ **Spring Session + Redis** ถ้ามีหลาย Instance

3. **`@CookieValue`**:
   - ใช้เก็บ **Language Preference** (`lang`)
   - ใช้เก็บ **Refresh Token** (HttpOnly, Secure)
   - ใช้เก็บ **Tracking ID** สำหรับ Analytics

4. **`@RequestHeader`**:
   - ใช้บ่อยที่สุดสำหรับ **Authorization Token** (JWT)
   - ใช้สำหรับ **Accept-Language** (การแปลภาษา)
   - ใช้สำหรับ **X-Request-ID** (การ Tracing)
   - ใช้สำหรับ **User-Agent** (การวิเคราะห์ Device)
   - ใช้ส่งต่อ Token ไปยัง Microservices อื่น

---
 # อธิบาย Annotation อื่นๆ (เพิ่มเติม) แบบละเอียด

ต่อเนื่องจากที่เราได้เรียนรู้ Annotation ต่างๆ มากมายแล้ว ขออธิบายเพิ่มเติมอีก 4 ตัวที่สำคัญไม่แพ้กัน:

- **`@CrossOrigin`** – การจัดการ CORS (Cross-Origin Resource Sharing) สำหรับ REST API
- **`@RestControllerAdvice`** – การจัดการ Exception แบบ Global (ฉบับสมบูรณ์)
- **`@ResponseBody`** – (เสริม) การแปลง Object → JSON (แม้ใช้ `@RestController` ไม่ต้องใช้ แต่ควรเข้าใจ)
- **`@JsonIgnore`** – (Jackson) การซ่อนฟิลด์ไม่ให้แสดงใน JSON Response

---

## 1. @CrossOrigin (CORS – Cross-Origin Resource Sharing)

### 1.1 ความหมายและจุดประสงค์

**`@CrossOrigin`** คือ Annotation ที่ใช้บอก Spring ว่า **"อนุญาตให้ Request มาจาก Origin (โดเมน) อื่นที่ไม่ใช่ของเรา"**

- ใช้กับ **Controller** หรือ **Method**
- ใช้เพื่อเปิดให้ Frontend (React, Angular, Vue) ที่รันบนคนละ Port หรือคนละโดเมน สามารถเรียก API ของเราได้
- หากไม่กำหนด Browser จะ block Request (CORS Policy)

### 1.2 ทำงานอย่างไร?

1. Browser ส่ง Request แบบ **Preflight** (`OPTIONS`) ไปยัง API เพื่อขออนุญาต
2. ถ้า API มี `Access-Control-Allow-Origin` ที่ตรงกับ Origin ของ Request → Browser อนุญาต
3. `@CrossOrigin` จะช่วยเพิ่ม Headers เหล่านี้ให้อัตโนมัติ

### 1.3 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: เปิด CORS ทั่วทั้ง Controller
```java
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")  // ✅ อนุญาตจาก React (Port 3000) เท่านั้น
public class JobController {
    // ทุกเมธอดในนี้จะยอมรับจาก localhost:3000
}

// หรืออนุญาตหลาย Origin
@CrossOrigin(origins = {"http://localhost:3000", "https://myfrontend.com"})
```

#### ตัวอย่างที่ 2: เปิด CORS เฉพาะ Method (ละเอียดกว่า)
```java
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    // ✅ เฉพาะเมธอดนี้เท่านั้นที่อนุญาต CORS
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @GetMapping("/public")
    public ResponseEntity<List<JobDTO>> getPublicJobs() {
        return ResponseEntity.ok(jobService.getPublicJobs());
    }

    // ❌ เมธอดอื่นๆ ไม่มี CORS (ถ้าไม่ได้ตั้ง Global)
    @GetMapping("/private")
    public ResponseEntity<List<JobDTO>> getPrivateJobs() {
        // เรียกจาก localhost:3000 ตรงนี้จะถูก Block (ถ้าไม่มี CORS)
        return ResponseEntity.ok(jobService.getPrivateJobs());
    }
}
```

#### ตัวอย่างที่ 3: กำหนด CORS แบบละเอียด (Methods, Headers, Credentials)
```java
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(
    origins = "http://localhost:3000",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
    allowedHeaders = {"Authorization", "Content-Type", "Accept"},
    exposedHeaders = {"X-Total-Count", "X-Page"},
    allowCredentials = "true",
    maxAge = 3600
)
public class AuthController {
    // ใช้กับทุกเมธอดใน Controller
}
```

#### ตัวอย่างที่ 4: ใช้ Global CORS (แนะนำ) – แทนการเขียน @CrossOrigin ทุกที่
```java
package com.template.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // ✅ ใช้กับทุก API ที่ขึ้นต้นด้วย /api
                .allowedOrigins("http://localhost:3000", "https://myfrontend.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("X-Total-Count", "X-Page")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

### 1.4 ข้อควรระวัง
| ปัญหา | วิธีแก้ไข |
|-------|----------|
| **ใช้ Credentials (Cookie, Authorization)** | ต้องตั้ง `allowCredentials = "true"` และ `allowedOrigins` ต้องเป็นค่าเฉพาะ (ไม่ใช้ `"*"`) |
| **Preflight OPTIONS ถูก Block** | ต้องอนุญาต `OPTIONS` Method และ `maxAge` เพื่อลดการ Preflight |
| **ใช้ใน Production** | ควรใช้ Environment Variable (`${CORS_ALLOWED_ORIGINS}`) แทนการ Hardcode |

---

## 2. @RestControllerAdvice (Global Exception – ฉบับเต็ม)

### 2.1 ความหมายและจุดประสงค์

**`@RestControllerAdvice`** คือ Annotation ที่ใช้บอก Spring ว่า **"คลาสนี้เป็น Global Exception Handler สำหรับ REST API"**

- มันคือ `@ControllerAdvice` + `@ResponseBody` (เหมือนกับ `@RestController`)
- ใช้เพื่อจัดการ Exception ทุกประเภทที่เกิดขึ้นในระบบแบบรวมศูนย์
- สามารถกำหนดขอบเขตได้ด้วย `assignableTypes`, `basePackages` เพื่อให้เฉพาะเจาะจง

### 2.2 แตกต่างจาก @ControllerAdvice อย่างไร?

| Annotation | คืนค่าเป็น | ใช้กับ |
|-----------|-----------|--------|
| **`@ControllerAdvice`** | View (HTML) | MVC (แบบส่งหน้า) |
| **`@RestControllerAdvice`** | JSON / XML | REST API |

### 2.3 ตัวอย่างในระบบของเรา

```java
package com.template.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice  // ✅ ใช้กับ REST API
public class GlobalExceptionHandler {

    // 1. จัดการ Validation Error (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    }

    // 2. จัดการ Request Body ที่อ่านไม่ได้ (JSON ผิด)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseException(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request", ex.getMessage());
    }

    // 3. จัดการ Custom Business Exception
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, Object>> handleDomainException(DomainException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Business rule violation", ex.getMessage());
    }

    // 4. จัดการ Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage());
    }

    // 5. จัดการ Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage());
    }

    // 6. จัดการ Exception อื่นๆ ทั้งหมด (ตกหล่น)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        log.error("Unexpected error: ", ex);  // log ไว้ดู
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", 
                "An unexpected error occurred");
    }

    // Helper method
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, Object message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
```

### 2.4 การจำกัดขอบเขต (เฉพาะบาง Controller)
```java
@RestControllerAdvice(assignableTypes = {JobController.class, QuotationController.class})
public class JobSpecificExceptionHandler {
    // เฉพาะ JobController และ QuotationController เท่านั้น
}
```

---

## 3. @ResponseBody (เสริม – แปลง Object → JSON)

### 3.1 ความหมายและจุดประสงค์

**`@ResponseBody`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้แปลง Object ที่คืนจากเมธอด ไปเป็น JSON/XML และใส่ใน Response Body"**

- ใช้กับ **เมธอด** ใน Controller
- ถ้าใช้ `@RestController` แล้ว **ไม่ต้องเขียน** เพราะมีแล้ว
- ถ้าใช้ `@Controller` (แบบ MVC) ต้องเขียนเพื่อให้คืน JSON

### 3.2 ตัวอย่าง (เพื่อความเข้าใจ)
```java
// ใช้ @Controller (แบบ MVC)
@Controller
public class OldController {
    
    @GetMapping("/user")
    @ResponseBody  // ✅ จำเป็น เพื่อบอกให้แปลงเป็น JSON
    public User getUser() {
        return new User("John");
    }
}

// ใช้ @RestController (ไม่ต้องเขียน @ResponseBody)
@RestController
public class NewController {
    
    @GetMapping("/user")
    public User getUser() {  // ✅ ไม่ต้องเขียน @ResponseBody
        return new User("John");
    }
}
```

> สรุป: ในระบบของเราใช้ `@RestController` อยู่แล้ว ดังนั้นไม่ต้องกังวลกับ `@ResponseBody` แต่ควรเข้าใจว่ามันคืออะไร

---

## 4. @JsonIgnore (Jackson – ซ่อนฟิลด์ไม่ให้แสดงใน JSON)

### 4.1 ความหมายและจุดประสงค์

**`@JsonIgnore`** คือ Annotation จาก Library **Jackson** ที่ใช้บอกว่า **"อย่า Serialize (แปลง) ฟิลด์นี้ไปเป็น JSON"** หรือ **"อย่า Deserialize (รับ) ฟิลด์นี้จาก JSON"**

- ใช้กับ **Field** หรือ **Getter/Setter** ใน Entity / DTO
- ช่วยป้องกันการเปิดเผยข้อมูลที่ละเอียดอ่อน เช่น `passwordHash`, `createdAt`, `deletedAt`
- ป้องกันไม่ให้ Client ส่งข้อมูลที่เราไม่ต้องการให้เปลี่ยนแปลง (เช่น `id`, `createdAt`)

### 4.2 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: ซ่อน Password Hash ใน Domain Entity
```java
package com.template.app.modules.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MUser {
    private UUID id;
    private String username;
    private String email;
    
    @JsonIgnore  // ✅ จะไม่ถูกส่งออกไปใน JSON (ไม่แสดง Password)
    private String passwordHash;
    
    @JsonIgnore  // ✅ ซ่อน status ที่เป็นข้อมูลภายใน
    private UserStatus status;
    
    private String fullName;
}
```

#### ตัวอย่างที่ 2: ซ่อนฟิลด์ที่ Client ไม่ควรส่งกลับมา (Response DTO)
```java
package com.template.app.modules.job.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class JobResponseDTO {
    private UUID id;
    private String jobNo;
    private String customerName;
    private String carModel;
    private String status;
    
    @JsonIgnore  // ✅ Client ไม่ควรรู้ว่าใครเป็นคนสร้าง (หรือใช้เฉพาะ Admin)
    private String createdBy;
    
    @JsonIgnore  // ✅ ซ่อนข้อมูลลบ (soft delete)
    private Boolean deleted;
}
```

#### ตัวอย่างที่ 3: ซ่อนฟิลด์เมื่อรับ Request (Client ส่งมาทำให้เกิดปัญหา)
```java
package com.template.app.modules.job.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobUpdateRequestDTO {
    
    @JsonIgnore  // ✅ Client ไม่ควรส่ง id มา (ใช้จาก @PathVariable แทน)
    private UUID id;
    
    @NotBlank
    private String symptom;
    
    private String diagnosisNote;
    
    @JsonIgnore  // ✅ Client ไม่ควรแก้ไข status โดยตรง (ใช้ API แยก)
    private String status;
}
```

#### ตัวอย่างที่ 4: @JsonIgnoreProperties (ซ่อนหลายฟิลด์พร้อมกัน)
```java
@Data
@JsonIgnoreProperties({"createdAt", "updatedAt", "deletedAt", "deleted"})
public class JobResponseDTO {
    private UUID id;
    private String jobNo;
    // ฟิลด์ createdAt, updatedAt, deletedAt, deleted จะไม่ถูก Serialize
}
```

#### ตัวอย่างที่ 5: @JsonIgnore + @JsonProperty (ซ่อนเฉพาะตอน Serialize แต่รับได้)
```java
@Data
public class UserDTO {
    private String username;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // ✅ รับได้ (Deserialize) แต่ไม่ส่งออก
    private String password;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)   // ✅ ส่งออกได้ แต่ไม่รับ (ใช้สำหรับ computed field)
    private String fullName;
}
```

### 4.3 ข้อควรระวัง
| ปัญหา | วิธีแก้ไข |
|-------|----------|
| **ซ่อนข้อมูลใน Domain แต่ไม่ซ่อนใน DTO** | ใช้ DTO เฉพาะสำหรับ Response (ไม่ใช้ Domain โดยตรง) |
| **ใช้ @JsonIgnore กับ Setter เท่านั้น** | ถ้าต้องการรับค่าได้แต่ไม่ส่งออก ใช้ `@JsonProperty(access = Access.WRITE_ONLY)` |
| **ลืมซ่อนข้อมูลอ่อนไหว** | ควรตรวจสอบ DTO ทุกตัวว่ามีฟิลด์ที่ควรซ่อนหรือไม่ |

---

## 5. สรุปตารางเปรียบเทียบ

| Annotation | ใช้ที่ไหน | หน้าที่ | ตัวอย่าง |
|-----------|---------|--------|----------|
| **`@CrossOrigin`** | Class / Method | อนุญาต CORS (เรียกข้ามโดเมน) | `@CrossOrigin("http://localhost:3000")` |
| **`@RestControllerAdvice`** | Class | Global Exception Handler (REST) | `@RestControllerAdvice public class GlobalExceptionHandler` |
| **`@ResponseBody`** | Method | แปลง Object → JSON (อยู่ใน `@RestController` แล้ว) | `@ResponseBody public User getUser()` |
| **`@JsonIgnore`** | Field / Getter/Setter | ซ่อนฟิลด์ไม่ให้ Serialize/Deserialize | `@JsonIgnore private String passwordHash` |

---

## 6. ตัวอย่างการใช้งานร่วมกันในระบบของเรา

```java
package com.template.app.modules.customer.presentation.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.template.app.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = "http://localhost:3000")  // ✅ เปิด CORS ทั้ง Controller
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerCreateRequestDTO request) {
        CustomerResponseDTO response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable UUID id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }
}

// DTO ที่ใช้ @JsonIgnore
@Data
public class CustomerResponseDTO {
    private UUID id;
    private String fullName;
    private String phoneNumber;
    
    @JsonIgnore  // ✅ ไม่แสดง email ใน Response
    private String email;
    
    @JsonIgnore
    private String passwordHash;  // ✅ ไม่มีใน DTO อยู่แล้ว แต่ถ้ามีก็ซ่อน
}
```

---

## 7. คำแนะนำสำหรับโปรเจกต์ Auto Repair Shop

1. **`@CrossOrigin`**: 
   - ใช้ **Global CORS** ใน `WebConfig` แทนการเขียน `@CrossOrigin` ทุกที่
   - ใช้ Environment Variable สำหรับ Production (`${CORS_ORIGINS}`)

2. **`@RestControllerAdvice`**: 
   - ใช้จัดการ Exception ทั้งหมด (เป็นมาตรฐาน)
   - บันทึก Log ทุกครั้ง (`log.error`) เพื่อ Debug

3. **`@ResponseBody`**: 
   - เราใช้ `@RestController` อยู่แล้ว ไม่ต้องกังวล
   - แต่ถ้าต้องการคืนค่าเป็น View (HTML) ให้ใช้ `@Controller` แทน

4. **`@JsonIgnore`**:
   - ใช้ใน DTO ทุกตัว เพื่อป้องกันการเปิดเผยข้อมูลอ่อนไหว
   - ใช้ `@JsonProperty(access = Access.WRITE_ONLY)` สำหรับ Password
   - ใช้ `@JsonIgnoreProperties` สำหรับฟิลด์ Audit (createdAt, updatedAt, deletedAt)

---
 # อธิบาย Annotation อื่นๆ (เพิ่มเติม) แบบละเอียด

ต่อเนื่องจากที่เราได้เรียนรู้ Annotation ต่างๆ มากมายแล้ว ขออธิบายเพิ่มเติมอีก 4 ตัวที่สำคัญไม่แพ้กัน:

- **`@CrossOrigin`** – การจัดการ CORS (Cross-Origin Resource Sharing) สำหรับ REST API
- **`@RestControllerAdvice`** – การจัดการ Exception แบบ Global (ฉบับสมบูรณ์)
- **`@ResponseBody`** – (เสริม) การแปลง Object → JSON (แม้ใช้ `@RestController` ไม่ต้องใช้ แต่ควรเข้าใจ)
- **`@JsonIgnore`** – (Jackson) การซ่อนฟิลด์ไม่ให้แสดงใน JSON Response

---

## 1. @CrossOrigin (CORS – Cross-Origin Resource Sharing)

### 1.1 ความหมายและจุดประสงค์

**`@CrossOrigin`** คือ Annotation ที่ใช้บอก Spring ว่า **"อนุญาตให้ Request มาจาก Origin (โดเมน) อื่นที่ไม่ใช่ของเรา"**

- ใช้กับ **Controller** หรือ **Method**
- ใช้เพื่อเปิดให้ Frontend (React, Angular, Vue) ที่รันบนคนละ Port หรือคนละโดเมน สามารถเรียก API ของเราได้
- หากไม่กำหนด Browser จะ block Request (CORS Policy)

### 1.2 ทำงานอย่างไร?

1. Browser ส่ง Request แบบ **Preflight** (`OPTIONS`) ไปยัง API เพื่อขออนุญาต
2. ถ้า API มี `Access-Control-Allow-Origin` ที่ตรงกับ Origin ของ Request → Browser อนุญาต
3. `@CrossOrigin` จะช่วยเพิ่ม Headers เหล่านี้ให้อัตโนมัติ

### 1.3 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: เปิด CORS ทั่วทั้ง Controller
```java
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")  // ✅ อนุญาตจาก React (Port 3000) เท่านั้น
public class JobController {
    // ทุกเมธอดในนี้จะยอมรับจาก localhost:3000
}

// หรืออนุญาตหลาย Origin
@CrossOrigin(origins = {"http://localhost:3000", "https://myfrontend.com"})
```

#### ตัวอย่างที่ 2: เปิด CORS เฉพาะ Method (ละเอียดกว่า)
```java
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    // ✅ เฉพาะเมธอดนี้เท่านั้นที่อนุญาต CORS
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @GetMapping("/public")
    public ResponseEntity<List<JobDTO>> getPublicJobs() {
        return ResponseEntity.ok(jobService.getPublicJobs());
    }

    // ❌ เมธอดอื่นๆ ไม่มี CORS (ถ้าไม่ได้ตั้ง Global)
    @GetMapping("/private")
    public ResponseEntity<List<JobDTO>> getPrivateJobs() {
        // เรียกจาก localhost:3000 ตรงนี้จะถูก Block (ถ้าไม่มี CORS)
        return ResponseEntity.ok(jobService.getPrivateJobs());
    }
}
```

#### ตัวอย่างที่ 3: กำหนด CORS แบบละเอียด (Methods, Headers, Credentials)
```java
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(
    origins = "http://localhost:3000",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
    allowedHeaders = {"Authorization", "Content-Type", "Accept"},
    exposedHeaders = {"X-Total-Count", "X-Page"},
    allowCredentials = "true",
    maxAge = 3600
)
public class AuthController {
    // ใช้กับทุกเมธอดใน Controller
}
```

#### ตัวอย่างที่ 4: ใช้ Global CORS (แนะนำ) – แทนการเขียน @CrossOrigin ทุกที่
```java
package com.template.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // ✅ ใช้กับทุก API ที่ขึ้นต้นด้วย /api
                .allowedOrigins("http://localhost:3000", "https://myfrontend.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("X-Total-Count", "X-Page")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

### 1.4 ข้อควรระวัง
| ปัญหา | วิธีแก้ไข |
|-------|----------|
| **ใช้ Credentials (Cookie, Authorization)** | ต้องตั้ง `allowCredentials = "true"` และ `allowedOrigins` ต้องเป็นค่าเฉพาะ (ไม่ใช้ `"*"`) |
| **Preflight OPTIONS ถูก Block** | ต้องอนุญาต `OPTIONS` Method และ `maxAge` เพื่อลดการ Preflight |
| **ใช้ใน Production** | ควรใช้ Environment Variable (`${CORS_ALLOWED_ORIGINS}`) แทนการ Hardcode |

---

## 2. @RestControllerAdvice (Global Exception – ฉบับเต็ม)

### 2.1 ความหมายและจุดประสงค์

**`@RestControllerAdvice`** คือ Annotation ที่ใช้บอก Spring ว่า **"คลาสนี้เป็น Global Exception Handler สำหรับ REST API"**

- มันคือ `@ControllerAdvice` + `@ResponseBody` (เหมือนกับ `@RestController`)
- ใช้เพื่อจัดการ Exception ทุกประเภทที่เกิดขึ้นในระบบแบบรวมศูนย์
- สามารถกำหนดขอบเขตได้ด้วย `assignableTypes`, `basePackages` เพื่อให้เฉพาะเจาะจง

### 2.2 แตกต่างจาก @ControllerAdvice อย่างไร?

| Annotation | คืนค่าเป็น | ใช้กับ |
|-----------|-----------|--------|
| **`@ControllerAdvice`** | View (HTML) | MVC (แบบส่งหน้า) |
| **`@RestControllerAdvice`** | JSON / XML | REST API |

### 2.3 ตัวอย่างในระบบของเรา

```java
package com.template.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice  // ✅ ใช้กับ REST API
public class GlobalExceptionHandler {

    // 1. จัดการ Validation Error (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    }

    // 2. จัดการ Request Body ที่อ่านไม่ได้ (JSON ผิด)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseException(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request", ex.getMessage());
    }

    // 3. จัดการ Custom Business Exception
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, Object>> handleDomainException(DomainException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Business rule violation", ex.getMessage());
    }

    // 4. จัดการ Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage());
    }

    // 5. จัดการ Unauthorized
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage());
    }

    // 6. จัดการ Exception อื่นๆ ทั้งหมด (ตกหล่น)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        log.error("Unexpected error: ", ex);  // log ไว้ดู
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", 
                "An unexpected error occurred");
    }

    // Helper method
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, Object message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
```

### 2.4 การจำกัดขอบเขต (เฉพาะบาง Controller)
```java
@RestControllerAdvice(assignableTypes = {JobController.class, QuotationController.class})
public class JobSpecificExceptionHandler {
    // เฉพาะ JobController และ QuotationController เท่านั้น
}
```

---

## 3. @ResponseBody (เสริม – แปลง Object → JSON)

### 3.1 ความหมายและจุดประสงค์

**`@ResponseBody`** คือ Annotation ที่ใช้บอก Spring ว่า **"ให้แปลง Object ที่คืนจากเมธอด ไปเป็น JSON/XML และใส่ใน Response Body"**

- ใช้กับ **เมธอด** ใน Controller
- ถ้าใช้ `@RestController` แล้ว **ไม่ต้องเขียน** เพราะมีแล้ว
- ถ้าใช้ `@Controller` (แบบ MVC) ต้องเขียนเพื่อให้คืน JSON

### 3.2 ตัวอย่าง (เพื่อความเข้าใจ)
```java
// ใช้ @Controller (แบบ MVC)
@Controller
public class OldController {
    
    @GetMapping("/user")
    @ResponseBody  // ✅ จำเป็น เพื่อบอกให้แปลงเป็น JSON
    public User getUser() {
        return new User("John");
    }
}

// ใช้ @RestController (ไม่ต้องเขียน @ResponseBody)
@RestController
public class NewController {
    
    @GetMapping("/user")
    public User getUser() {  // ✅ ไม่ต้องเขียน @ResponseBody
        return new User("John");
    }
}
```

> สรุป: ในระบบของเราใช้ `@RestController` อยู่แล้ว ดังนั้นไม่ต้องกังวลกับ `@ResponseBody` แต่ควรเข้าใจว่ามันคืออะไร

---

## 4. @JsonIgnore (Jackson – ซ่อนฟิลด์ไม่ให้แสดงใน JSON)

### 4.1 ความหมายและจุดประสงค์

**`@JsonIgnore`** คือ Annotation จาก Library **Jackson** ที่ใช้บอกว่า **"อย่า Serialize (แปลง) ฟิลด์นี้ไปเป็น JSON"** หรือ **"อย่า Deserialize (รับ) ฟิลด์นี้จาก JSON"**

- ใช้กับ **Field** หรือ **Getter/Setter** ใน Entity / DTO
- ช่วยป้องกันการเปิดเผยข้อมูลที่ละเอียดอ่อน เช่น `passwordHash`, `createdAt`, `deletedAt`
- ป้องกันไม่ให้ Client ส่งข้อมูลที่เราไม่ต้องการให้เปลี่ยนแปลง (เช่น `id`, `createdAt`)

### 4.2 ตัวอย่างในระบบของเรา

#### ตัวอย่างที่ 1: ซ่อน Password Hash ใน Domain Entity
```java
package com.template.app.modules.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MUser {
    private UUID id;
    private String username;
    private String email;
    
    @JsonIgnore  // ✅ จะไม่ถูกส่งออกไปใน JSON (ไม่แสดง Password)
    private String passwordHash;
    
    @JsonIgnore  // ✅ ซ่อน status ที่เป็นข้อมูลภายใน
    private UserStatus status;
    
    private String fullName;
}
```

#### ตัวอย่างที่ 2: ซ่อนฟิลด์ที่ Client ไม่ควรส่งกลับมา (Response DTO)
```java
package com.template.app.modules.job.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class JobResponseDTO {
    private UUID id;
    private String jobNo;
    private String customerName;
    private String carModel;
    private String status;
    
    @JsonIgnore  // ✅ Client ไม่ควรรู้ว่าใครเป็นคนสร้าง (หรือใช้เฉพาะ Admin)
    private String createdBy;
    
    @JsonIgnore  // ✅ ซ่อนข้อมูลลบ (soft delete)
    private Boolean deleted;
}
```

#### ตัวอย่างที่ 3: ซ่อนฟิลด์เมื่อรับ Request (Client ส่งมาทำให้เกิดปัญหา)
```java
package com.template.app.modules.job.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobUpdateRequestDTO {
    
    @JsonIgnore  // ✅ Client ไม่ควรส่ง id มา (ใช้จาก @PathVariable แทน)
    private UUID id;
    
    @NotBlank
    private String symptom;
    
    private String diagnosisNote;
    
    @JsonIgnore  // ✅ Client ไม่ควรแก้ไข status โดยตรง (ใช้ API แยก)
    private String status;
}
```

#### ตัวอย่างที่ 4: @JsonIgnoreProperties (ซ่อนหลายฟิลด์พร้อมกัน)
```java
@Data
@JsonIgnoreProperties({"createdAt", "updatedAt", "deletedAt", "deleted"})
public class JobResponseDTO {
    private UUID id;
    private String jobNo;
    // ฟิลด์ createdAt, updatedAt, deletedAt, deleted จะไม่ถูก Serialize
}
```

#### ตัวอย่างที่ 5: @JsonIgnore + @JsonProperty (ซ่อนเฉพาะตอน Serialize แต่รับได้)
```java
@Data
public class UserDTO {
    private String username;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // ✅ รับได้ (Deserialize) แต่ไม่ส่งออก
    private String password;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)   // ✅ ส่งออกได้ แต่ไม่รับ (ใช้สำหรับ computed field)
    private String fullName;
}
```

### 4.3 ข้อควรระวัง
| ปัญหา | วิธีแก้ไข |
|-------|----------|
| **ซ่อนข้อมูลใน Domain แต่ไม่ซ่อนใน DTO** | ใช้ DTO เฉพาะสำหรับ Response (ไม่ใช้ Domain โดยตรง) |
| **ใช้ @JsonIgnore กับ Setter เท่านั้น** | ถ้าต้องการรับค่าได้แต่ไม่ส่งออก ใช้ `@JsonProperty(access = Access.WRITE_ONLY)` |
| **ลืมซ่อนข้อมูลอ่อนไหว** | ควรตรวจสอบ DTO ทุกตัวว่ามีฟิลด์ที่ควรซ่อนหรือไม่ |

---

## 5. สรุปตารางเปรียบเทียบ

| Annotation | ใช้ที่ไหน | หน้าที่ | ตัวอย่าง |
|-----------|---------|--------|----------|
| **`@CrossOrigin`** | Class / Method | อนุญาต CORS (เรียกข้ามโดเมน) | `@CrossOrigin("http://localhost:3000")` |
| **`@RestControllerAdvice`** | Class | Global Exception Handler (REST) | `@RestControllerAdvice public class GlobalExceptionHandler` |
| **`@ResponseBody`** | Method | แปลง Object → JSON (อยู่ใน `@RestController` แล้ว) | `@ResponseBody public User getUser()` |
| **`@JsonIgnore`** | Field / Getter/Setter | ซ่อนฟิลด์ไม่ให้ Serialize/Deserialize | `@JsonIgnore private String passwordHash` |

---

## 6. ตัวอย่างการใช้งานร่วมกันในระบบของเรา

```java
package com.template.app.modules.customer.presentation.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.template.app.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = "http://localhost:3000")  // ✅ เปิด CORS ทั้ง Controller
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerCreateRequestDTO request) {
        CustomerResponseDTO response = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable UUID id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }
}

// DTO ที่ใช้ @JsonIgnore
@Data
public class CustomerResponseDTO {
    private UUID id;
    private String fullName;
    private String phoneNumber;
    
    @JsonIgnore  // ✅ ไม่แสดง email ใน Response
    private String email;
    
    @JsonIgnore
    private String passwordHash;  // ✅ ไม่มีใน DTO อยู่แล้ว แต่ถ้ามีก็ซ่อน
}
```

---

## 7. คำแนะนำสำหรับโปรเจกต์ Auto Repair Shop

1. **`@CrossOrigin`**: 
   - ใช้ **Global CORS** ใน `WebConfig` แทนการเขียน `@CrossOrigin` ทุกที่
   - ใช้ Environment Variable สำหรับ Production (`${CORS_ORIGINS}`)

2. **`@RestControllerAdvice`**: 
   - ใช้จัดการ Exception ทั้งหมด (เป็นมาตรฐาน)
   - บันทึก Log ทุกครั้ง (`log.error`) เพื่อ Debug

3. **`@ResponseBody`**: 
   - เราใช้ `@RestController` อยู่แล้ว ไม่ต้องกังวล
   - แต่ถ้าต้องการคืนค่าเป็น View (HTML) ให้ใช้ `@Controller` แทน

4. **`@JsonIgnore`**:
   - ใช้ใน DTO ทุกตัว เพื่อป้องกันการเปิดเผยข้อมูลอ่อนไหว
   - ใช้ `@JsonProperty(access = Access.WRITE_ONLY)` สำหรับ Password
   - ใช้ `@JsonIgnoreProperties` สำหรับฟิลด์ Audit (createdAt, updatedAt, deletedAt)

---
 