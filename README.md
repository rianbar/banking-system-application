# Banking system Based on PicPay challenge! 💰
 
### *Summary:* Well, this project includes two types of user, common user and shopkeeper, which has different functions and restrictions in application. Both have a wallet and can be filled with any amount.
<br>

| Routes | Description |
| --- | --- |
| `http://host:8000/user/{id}` | get user by id user on database|
| `http://host:8000/user/register` | register user on database|
| `http://host:8000/user/login` | use valid credentials to get an access code|
| `http://host:8000/user/update/{id}` | update a user on database|
| `http://host:8000/user/delete/{id}` | delete an user on database|
| `http://host:8100/tbu/transaction` | transfer value|

### Register user: 
```Java
{
    "name": "anyName",
    "identity": "090.666.664-65",
    "email": "johnDoe@email.com",
    "password": "anypassword",
    "userType": "USER/SHOPKEEPER"
}
```
To register a user, you have to fill in payload. In the **userType** you should to fill in **user** or **shopkeeper** and send in the body request.

### Login user: 
```Java
{
    "email": "johnDoe@email.com",
    "password": "anypassword",
}
```
You should send this payload and if the credentials are valid you will receive a `JWT Acess code` that will be used to access any other endpoint.

### Update user:
> [!IMPORTANT]<br>
> update user endpoint use the same payload as register endpoint.

rules for payload fields:

| Field | Rule |
| --- | --- |
| `name` | cannot be empty|
| `identity` | must be a value that does not exist in the database|
| `email` | must be a value that does not exist in the database|
| `password` | cannot be empty|
| `userType` | must be user or shopkeeper|

> [!NOTE]<br>
> To delete or get a user you cannot do nothing, just send a valid `id` on the intended endpoint URL. <br>
> To access update and delete endpoint, you must put the access code in the request header.

## Transaction Service 💳

### Transaction Rules

- Ordinary user can send and receive transaction amounts.
- Shopkeeper can only receive transactions.
- To access transaction endpoint, user can be authenticated.
- To transfer an amount, you must have it in your wallet.

```Java
{
    "value": 2500,
    "payer": 3,
    "payee": 5
}
```
To transfer an amount, you must use the payer and payee id and both must exist in the database.

## Dependencies❗
```bash
$ docker run --name mysql_container -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0
```

