## 📌 Sample Payload: Create Appointment

Use the following JSON when creating a new appointment via the API:

```json
{
  "patientId": "104",
  "doctorId": "201",
  "status": "Booked"
}

GET appointment by id http://localhost:8080/api/appointments/2

GET appointment by DR id GET http://localhost:8080/api/appointments/doctor/201