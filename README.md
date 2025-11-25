# Task Management System

Spring Boot ile geliştirilmiş, kullanıcı oluşturma ve bu kullanıcılara görev (task) atama özelliklerine sahip bir **Task Management System** uygulamasıdır. Uygulama; katmanlı mimari, clean code prensipleri, global exception handling ve MapStruct ile tip dönüşümlerine odaklanarak ölçeklenebilir bir altyapı sunar.

---

## Teknolojiler

### **Backend**
- **Java 17**
- **Spring Boot**  
- **PostgreSQL**
- **MapStruct** – DTO ↔ Entity dönüşümleri
- **Lombok** – Boilerplate kodların azaltılması
- **GlobalExceptionHandler** – Merkezî hata yönetimi
- **Katmanlı Mimari** – Clean code prensiplerine uygun yapı

---

## Özellikler

###  Kullanıcı Yönetimi
- Yeni kullanıcı oluşturma
- Kullanıcı listesi görüntüleme
- Kullanıcı detaylarını görme
- Kullanıcı silme

###  Task Yönetimi
- Kullanıcıya task atama
- Departmanlara özel task oluşturma, güncelleme, silme 
- Task durum yönetimi:  
  **ACTIVE → PENDING → DONE**
- Kullanıcıya ait task listesini görüntüleme

###  Teknik Özellikler
- **MapStruct** ile hızlı ve güvenli DTO dönüşümleri
- **GlobalExceptionHandler** ile tutarlı ve standart hata yönetimi
- **Clean Code** prensiplerine uygun temiz, düzenli ve sürdürülebilir kod yapısı

---

##  Database

Uygulama **PostgreSQL** üzerinde şu temel yapıları kullanır:

- **user** tablosu  
- **task** tablosu  
  - `ManyToMany` -> `user_id` -> `task_id` ile ilişkilidir.

---

##  Projeyi Çalıştırma

### 1️. PostgreSQL’i başlat  
Aşağıdaki komutla yeni bir veritabanı oluştur:

```sql
CREATE DATABASE {db name};
```

### 2. application.properties düzenle  
````
spring.application.name=taskify
spring.datasource.url=jdbc:postgresql://localhost:5432/{db name}
spring.jpa.properties.hibernate.default_schema= {db schema name}

spring.datasource.username={db username}

spring.datasource.password={db password}

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format_sql=true
````

### 3. Uygulamayı çalıştır  
````
mvn spring-boot:run
````
---

## API Endpoint Örnekleri

### Kullanıcı Oluşturma
````
{
    "name":"deneme",
    "surname":"deneme",
    "username":"deneme",
    "email":"deneme@gmail.com"
}
````

### Task Oluşturma
````
{
    "taskStatus": "ACTIVE",
    "taskGroup": "Technology",
    "taskTitle": "Years End Meeting",
    "taskDetail": "Years end meeting",
    "usernames": ["deneme"]
}
````
---

## Clean Code Yaklaşımı
- Katmanlar sorumluluklara göre net ayrıldı.
- DTO dönüşümleri MapStruct ile düzenli hale getirildi.
- Controller katmanı sadece yönlendirme işlemlerini üstlendi.
- Service katmanında iş kuralları konumlandırıldı.
- Exception yapısı sade, merkezi ve genişletilebilir şekilde tasarlandı.
