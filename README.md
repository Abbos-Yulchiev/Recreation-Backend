# Smart City Recreation Module

## Using Recreation info

### Admin's case

1. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/admin/UserList Send a request with POST method to create a new User.

   Request body `{"citizenId":"11111112","username":"newAdmin","password":"admin","prePassword":"admin","roles":["ADMIN"]}`


2. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/recreation/add Send a request with POST method to create a new
   Recreation place.

   Request body `{"name":"AkvaParks","description":"For young children and adults","availableSits":250,"openingTime":"2021-10-02 09:00:00","
   closingTime":"2021-10-02 23:00:00","recreationCategory":"PARK","recreationStatus":"OPEN","address":{"id":102,"homeCode":10102,"homeNumber":1,
   "building": {"id":5,"street":{"id":1,"name":"Virginia Avenue","district":{"id":1,"name":"Arbuckle"}}, "buildingNumber":5,
   buildingType":"COMMERCIAL_BUILDINGS"}}}`

3. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/event/add Send a request with POST method to create a new Event.

   Request body `{"name":"Test request","description": "Extra cheking evants","startTime": "2021-10-18 09:00:00","endTime": "2021-10-20 15:00:00",
   "availableSits": 100,"eventType": "FESTIVAL","eventStatus":"ACTIVE","recreationId": [1]}`

4. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/event/all Send a request with GET method to get Event list.

   Response body `[{"id": 1,"createdAt": "2021-11-14T11:14:51.235+00:00","updatedAt": "2021-11-14T11:14:51.235+00:00","createdBy": null,"updatedBy": null,"name": "Tremankano","confirmed": true,"description": "New Recreation Place","startTime": "2021-11-14T16:14:33","endTime": "2021-12-14T16:14:33","availableSits": 50,"eventType": "FESTIVAL","eventStatus": "ACTIVE","recreations": [{"id": 6,"name": "Contalorindasoe",
"description": "New Recreation","availableSits": 20,"openingTime": "2021-11-05T07:00:00","closingTime": "2021-11-05T20:30:00","recreationCategory": "PARK","recreationStatus": "CLOSED","address": {"addressId": 2,"id": 111,"homeCode": 10111,"homeNumber": "1","building": {"buildingId": 5,"id": 5,
"street": {"streetId": 5,"id": 5,"name": "Wood Street","district": {"districtId": 5,"id": 3,"name": "Bogalusa"}},"buildingNumber": 5,"buildingType": "COMMERCIAL_BUILDINGS"},"ownerCardNumber": "11111112"},"exist": true,"price": 12.5}],"isDeleted": null}]`

5. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/recreation/all Send a request with GET method to  get Recreation list.

   Response body `[{"id": 3,"name": "Tremankano","description": "New Recreation Place","availableSits": 100,"openingTime":"2021-11-03T11:23:42","closingTime":"2021-11-03T11:23:42", "recreationCategory":"PARK", "recreationStatus":"OPEN", "address": {"addressId":3, "id":111, "homeCode":10111,"homeNumber":"1", "building": {"buildingId":1, "id":21, "street": {"streetId":1, "id":5, "name":"Wood Street", "district":{"districtId":1, "id":1 "name":"Arbuckle"}},"buildingNumber":1, "buildingType":"COMMERCIAL_BUILDINGS"}, "ownerCardNumber":"11111115"}, "exist": true, "price":18.0}]`

### User's case

1. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/recreation/allByExist Send a request with GET method to  get Recreation list.

   Response body `{"id": 3,"name": "Tremankano","description": "New Recreation Place","availableSits": 100,
   "openingTime": 2021-11-03T11:23:42", "closingTime": "2021-11-03T11:23:42", "recreationCategory": "PARK", "recreationStatus": "OPEN",
   "address": {"addressId": 3,"id": 111,"homeCode": 10111,"homeNumber": "1","building": {"buildingId": 1,"id": 21,"street": {"streetId": 1,
   "id": 5,"name": "Wood Street","district":{"districtId": 1,"id": 1,"name": "Arbuckle"}},"buildingNumber": 1,"buildingType":COMMERCIAL_BUILDINGS"
   },"ownerCardNumber": "11111115"},"exist": true,"price": 18.0}`

2. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/event/allNotDelete  Send a request with GET method to  get Event list.

   Response body `{"id": 3,"createdAt": "2021-11-15T16:37:48.802+00:00","updatedAt": "2021-11-15T16:37:48.802+00:00","createdBy": null
   "updatedBy":null,"name": "Contalorindasoe","confirmed": true,"description": "New Recreation event","startTime": "2021-11-20T08:00:00","endTime": 
   "2021-12-30T23:00:00","availableSits": 20,"eventType": "FESTIVAL","eventStatus": "ACTIVE","isDeleted": null}],"pageable": {"sort": {"sorted": 
   false,"unsorted": true,"empty": true},"offset": 0,"pageSize": 20,"pageNumber": 0,"unpaged": false,"paged": true},"last": true,"totalPages": 1,
   "totalElements": 2,"size": 20,"number": 0,"sort": {"sorted": false,"unsorted": true,"empty": true},"numberOfElements": 2,"first": true
   "empty":false}`
3. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/order/recreation  Send a request with GET method to get User's ordered Recreation places list.

   Response body `["id":"44","booking_date":"2021-11-25T11:53:52.465+00:00","creation_date":"2021-11-25T11:53:52.465+00:00","paid":false,
   recreation_price": 9.0, "name":"Tremankano","order_price":18.0,"visiting_time":"2021-11-25T11:53:46.000+00:00"
   "leaving_time":"2021-11-25T11:23:46.000+00:00", "visitors_number":1]`

4. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/order/tickets  Send a request with GET method to get User's ordered Tickets list.

   Response body `[{"id":"27", "booking_date":"2021-11-16T13:34:05.000+00:00","creation_date":"2021-11-16T13:29:04.304+00:00", "paid":true,
   "name":"Tremankano","price"16.4}]`

5. http://smart-city-recreation-client.s3-website.eu-central-1.amazonaws.com/commentary/all?recreationId=1  Send a request with the Get method to get the User's comment about a Recreation place.

   Response body `[{"comment_text":"Great place to get relax! I can recoment to eveyone","first_name":"James","last_name":"William"
   "created_at":"2021-11-15T07:36:40.137+00:00"}]`
