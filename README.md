# Akademia Tarnowska
## Kurs
Testowanie i Jakość Oprogramowania / Projekt

## Autor
Mateusz Czosnyka

## Temat projektu
Sklep z Perfumami

## Opis projektu
Sklep umożliwia przeglądanie i zamawianie perfum online.
Użytkownik ma możliwość założenia konta i logowania się.
Administrator może zarządzać perfumami i zamówieniami.

## Uruchomienie projektu
1. Skopiuj projekt.
2. Uruchom bazę danych PostgreSQL o nazwie "perfume", na porcie "5432", z użytkownikiem o nazwie "postgres" i hasłem "root".
3. Uruchom serwer, korzystając z pliku Application.java.
Sklep będzie dostępny pod adresem http://localhost:8080

## Uruchomienie testów jednostkowych i integracyjnych
1. Uruchom bazę danych PostgreSQL o nazwie "perfumetest", na porcie "5432", z użytkownikiem o nazwie "postgres" i hasłem "root".
2. Uruchom testy w folderze tests.

## Dokumentacja API

### AdminController

1. **getPerfumes**
    - Adres usługi: /admin/perfumes
    - Typ: GET
    - Przyjmuje:
    - Zwraca: admin-perfumes.html
   
2. **searchPerfumes**
    - Adres usługi: /admin/perfumes/search
    - Typ: GET
    - Przyjmuje: {"searchType": "perfumeTitle", "text": "Aventus"}
    - Zwraca: admin-perfumes.html
   
3. **getUsers**
    - Adres usługi: /admin/users
    - Typ: GET
    - Przyjmuje:
    - Zwraca: admin-all-users.html
   
4. **searchUsers**
    - Adres usługi: /admin/users/search
    - Typ: GET
    - Przyjmuje: {"searchType": "firstName", "text": "userFirstName"}
    - Zwraca: admin-all-users.html
   
5. **getOrder**
    - Adres usługi: /admin/order/{orderId}
    - Typ: GET
    - Przyjmuje: {"orderId": 1}
    - Zwraca: order.html
   
6. **getOrders**
    - Adres usługi: /admin/orders
    - Typ: GET
    - Przyjmuje:
    - Zwraca: orders.html
   
7. **searchOrders**
    - Adres usługi: /admin/orders/search
    - Typ: GET
    - Przyjmuje: {"searchType": "firstName", "text": "userFirstName"}
    - Zwraca: orders.html
   
8. **getPerfume**
    - Adres usługi: /admin/perfume/{perfumeId}
    - Typ: GET
    - Przyjmuje: {"perfumeId": 1}
    - Zwraca: admin-edit-perfume.html
   
9. **editPerfume**
    - Adres usługi: /admin/edit/perfume
    - Typ: POST
    - Przyjmuje: {"id": 1, "perfumeTitle": "newTitle", "perfumer": "newPerfumer", "year": 2023, "country": "newCountry", "perfumeGender": "male", "fragranceTopNotes": "newFragranceTopNotes", "fragranceMiddleNotes": "newFragranceMiddleNotes", "fragranceBaseNotes": "newFragranceBaseNotes", "description": "newDescription", "filename", "newFilename", "price": 100, "volume", "newVolume", "type": "newType", "file": newImageBinary}
    - Zwraca: /admin/perfumes lub admin-edit-perfume.html
   
10. **addPerfume**
    - Adres usługi: /admin/add/perfume
    - Typ: GET
    - Przyjmuje:
    - Zwraca: admin-add-perfume.html
    
11. **addPerfume**
    - Adres usługi: /admin/add/perfume
    - Typ: POST
    - Przyjmuje: {"perfumeTitle": "newTitle", "perfumer": "newPerfumer", "year": 2023, "country": "newCountry", "perfumeGender": "male", "fragranceTopNotes": "newFragranceTopNotes", "fragranceMiddleNotes": "newFragranceMiddleNotes", "fragranceBaseNotes": "newFragranceBaseNotes", "description": "newDescription", "filename", "newFilename", "price": 100, "volume", "newVolume", "type": "newType", "file": imageBinary}
    - Zwraca: /admin/perfumes lub admin-add-perfume.html
    
12. **getUserById**
    - Adres usługi: /admin/user/{userId}
    - Typ: GET
    - Przyjmuje: {"userId": 1}
    - Zwraca: admin-user-detail.html

### AuthenticationController

1. **forgotPassword**
    - Adres usługi: /auth/forgot
    - Typ: GET
    - Przyjmuje:
    - Zwraca: forgot-password.html
   
2. **forgotPassword**
    - Adres usługi: /auth/forgot
    - Typ: POST
    - Przyjmuje: {"email": "email@email.email"}
    - Zwraca: forgot-password.html
   
3. **resetPassword**
    - Adres usługi: /auth/reset/{code}
    - Typ: GET
    - Przyjmuje: {"code": "resetCode"}
    - Zwraca: reset-password.html
   
4. **resetPassword**
    - Adres usługi: /auth/reset
    -  Typ: POST
    - Przyjmuje: {"email": "email@email.email", "password": "password", "password2", "password"}
    - Zwraca: /login lub reset-password.html

### CartController

1. **getCart**
    - Adres usługi: /cart
    - Typ: GET
    - Przyjmuje: 
    - Zwraca: cart.html
   
2. **addPerfumeToCart**
    - Adres usługi: /cart/add
    - Typ: POST
    - Przyjmuje: {"perfumeId": 1}
    - Zwraca: /cart
   
3. **removePerfumeFromCart**
    - Adres usługi: /cart/remove
    - Typ: POST
    - Przyjmuje: {"perfumeId": 1}
    - Zwraca: /cart

### CommonErrorController

1. **handleError**
    - Adres usługi: /error
    - Typ: GET
    - Przyjmuje:
    - Zwraca: error-500.html lub error-404.html

### HomeController

1. **home**
    - Adres usługi: /
    - Typ: GET
    - Przyjmuje:
    - Zwraca: home.html


### OrderController

1. **getOrder**
    - Adres usługi: /order/{orderId}
    - Typ: GET
    - Przyjmuje: {"orderId": 1}
    - Zwraca: order.html
   
2. **getOrdering**
    - Adres usługi: /order
    - Typ: GET
    - Przyjmuje:
    - Zwraca: ordering.html
   
3. **getUserOrdersList**
    - Adres usługi: /order/user/orders
    - Typ: GET
    - Przyjmuje:
    - Zwraca: orders.html
   
4. **postOrder**
    - Adres usługi: /order
    - Typ: POST
    - Przyjmuje:
    - Zwraca: order-finalize.html lub ordering.html

### PerfumeController

1. **getPerfumeById**
    - Adres usługi: /perfume/{perfumeId}
    - Typ: GET
    - Przyjmuje: {"perfumeId": 1}
    - Zwraca: perfume.html
   
2. **getPerfumesByFilterParams**
    - Adres usługi: /perfume
    - Typ: GET
    - Przyjmuje: {"perfumers": ["perfumerName"], "genders": ["male"], "price": 100}
    - Zwraca: perfumes.html
   
3. **searchPerfumes**
    - Adres usługi: /perfume/search
    - Typ: GET
    - Przyjmuje: {"searchType": "perfumeTitle", "text": "Aventus"}
    - Zwraca: perfumes.html

### RegistrationController

1. **registration**
    - Adres usługi: /registration/{perfumeId}
    - Typ: GET
    - Przyjmuje:
    - Zwraca: registration.html
   
2. **registration**
    - Adres usługi: /registration
    - Typ: POST
    - Przyjmuje: {"captchaResponse": "captchaResponse", "email": "email", "firstName": "firstName", "password": "password", "password2": "password"}
    - Zwraca: login.html lub registration.html
   
3. **activateEmailCode**
    - Adres usługi: /registration/activate/{code}
    - Typ: GET
    - Przyjmuje: {"code": "code"}
    - Zwraca: login.html

### UserController

1. **contacts**
    - Adres usługi: /user/contacts
    - Typ: GET
    - Przyjmuje:
    -  Zwraca: contacts.html
   
2. **passwordReset**
    - Adres usługi: /user/reset
    - Typ: GET
    - Przyjmuje:
    - Zwraca: user-password-reset.html
   
3. **userAccount**
    - Adres usługi: /user/account
    - Typ: GET
    - Przyjmuje:
    - Zwraca: user-account.html
   
4. **searchUserOrders**
    - Adres usługi: /user/orders/search
    - Typ: GET
    - Przyjmuje: {"searchType": "firstName", "text": "usersFirstName"}
    - Zwraca: orders.html
   
5. **userInfo**
    - Adres usługi: /user/info
    - Typ: GET
    - Przyjmuje:
    - Zwraca: user-info.html
   
6. **editUserInfo**
    - Adres usługi: /user/info/edit
    - Typ: GET
    - Przyjmuje:
    - Zwraca: user-info-edit.html
   
7. **editUserInfo**
    - Adres usługi: /user/info/edit
    - Typ: POST
    - Przyjmuje: {"firstName": "firstName", "lastName": "lastName", "city": "city", "address": "address", "phoneNumber": "phoneNumber", "postIndex": "postIndex", "email": "email"}
    - Zwraca: /user/info lub user-info-edit.html
   
8. **changePassword**
    - Adres usługi: /user/change/password
    - Typ: POST
    - Przyjmuje: {"password": "password", "password2": "password"}
    - Zwraca: user-password-reset.html

## Scenariusze testowe dla testera manualnego

1. **Przeglądanie produktów:**
    - Otwórz stronę z produktami(http://localhost:8080/perfume).
    - Sprawdź, czy poprawnie wyświetlają się produkty.

2. **Filtrowanie produktów:**
    - Otwórz stronę z produktami(http://localhost:8080/perfume).
    - Zaznacz filtry(Brand->Burberry, Gender->Female, Price->10-60$).
    - Sprawdź, czy poprawnie wyświetlają się produkty(ilość->3, nazwa->"Weekend For Women", "Women", "My Burberry").

3. **Wyszukiwanie produktów po tytule:**
    - Otwórz stronę z produktami(http://localhost:8080/perfume).
    - Zmień select na "Perfume title".
    - Wprowadź nazwę produktu(np. "Mr. Burberry").
    - Kliknij przycisk "Search".
    - Sprawdź czy poprawnie wyświetla się produkt.

4. **Przeglądanie informacji o produkcie:**
    - Otwórz stronę z produktami(http://localhost:8080/perfume).
    - Kliknij przycisk "SHOW MORE" dla przykładowego produktu.
    - Sprawdź, czy poprawnie wyświetlają się informacje o produkcie.

5. **Rejestrowanie użytkownika**
    - Otwórz stronę rejestrowania(http://localhost:8080/registration).
    - Wprowadź dene nowego użytkownika.
    - Zaznacz "Nie jestem robotem".
    - Kliknij przycisk "Sign up".
    - Otwórz pocztę email i potwierdź adres email(http://localhost:8080/registration/activate/{code}).
    - Sprawdź, czy poprawnie stworzono nowe konto użytkownika(punkt 6).

6. **Logowanie użytkownika:**
    - Otwórz stronę logowania(http://localhost:8080/login).
    - Wprowadź dane logowania(login->test123@test.com, hasło->admin).
    - Sprawdź, czy użytkownik jest poprawnie zalogowany.

7. **Dodawanie produktu do koszyka:**
    - Zaloguj się(punkt 6).
    - Otwórz stronę z perfumami(http://localhost:8080/perfume).
    - Kliknij przycisk "SHOW MORE" dla przykładowego produktu.
    - Kliknij przycisk "ADD TO CART".
    - Sprawdź czy produkt poprawnie dodał się do koszyka.

8. **Tworzenie zamówienia:**
    - Zaloguj się(punkt 6).
    - Dodaj przykładowy produkt do koszyka(punkt 7).
    - Otwórz stronę z koszykiem(http://localhost:8080/cart).
    - Kliknij przycisk "Checkout"(http://localhost:8080/order).
    - Wprowadź dane do wysyłki(Name->name, Last name->lastName, City->city, Address->address, Index->12345, Mobile->123456789, Email->test123@test.com)
    - Kliknij przycisk "Validate order".
    - Sprawdź, czy zamówienie zostało poprawnie stworzone.

9. **Przeglądanie historii zamówień:**
    - Zaloguj się(punkt 6).
    - Otwórz stronę użytkownika(http://localhost:8080/user/account)
    - Kliknij przycisk "List of orders"(http://localhost:8080/order/user/orders).
    - Sprawdź czy poprawnie wyświetlają się zamówienia.

10. **Przeglądanie zamówienia:**
    - Zaloguj się(punkt 6).
    - Otwórz stronę z historią zamówień(punkt 9).
    - Kliknij przycisk "Show more" dla przykładowego zamówienia(http://localhost:8080/order/{orderId}).
    - Sprawdź, czy poprawnie wyświetlają się informacje o zamówieniu.

11. **Przeglądanie informacji o użytkowniku:**
    - Zaloguj się(punkt 6).
    - Kliknij przycisk "Personal data"(http://localhost:8080/user/info).
    - Sprawdź, czy poprawnie wyświetlają się informację o użytkowniku.
    
12. **Edytowanie informacji o użytkowniku:**
    - Zaloguj się(punkt 6).
    - Otwórz stronę z informacjami o użytkowniku(http://localhost:8080/user/info).
    - Kliknij przycisk "Edit"(http://localhost:8080/user/info/edit).
    - Wprowadź nowe dane użytkownika.
    - Kliknij przycisk "Save".
    - Sprawdź, czy poprawnie zapisały się nowe dane użytkownika.

13. **Zmiana hasła użytkownika**
    - Zaloguj się(punkt 6).
    - Kliknij przycisk "Change password"(http://localhost:8080/user/reset).
    - Wprowadź nowe hasło.
    - Kliknij przycisk "Change".
    - Sprawdź, czy hasło zostało poprawnie zmienione.

## Technologie użyte w projekcie
- Java 1.8
- Spring-Boot
- Maven
- PostgreSQL
- HTML
- CSS
- JavaScript
