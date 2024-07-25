# user-access-management

### Configuration
Set your environment variables with respect to database connection in application.properties 

### JWT Secret Key
Replace the service.JwtService.SECRET_KEY with the key of size >= 256 bits

Since, The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size).