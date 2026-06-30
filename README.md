# 🔐 Password Manager 2026

> Un'applicazione desktop Java per la gestione sicura delle credenziali, con interfaccia grafica Swing, cifratura AES-256 e persistenza su database PostgreSQL.

    

***

## 📋 Descrizione

**Password Manager 2026** è un'applicazione desktop sviluppata in Java che consente agli utenti di registrarsi, autenticarsi e gestire in modo sicuro le proprie credenziali di accesso a siti e servizi. Tutte le password salvate sono cifrate con **AES-256-CBC** derivando la chiave dalla password master dell'utente tramite **PBKDF2** — il che significa che nessuna password è mai leggibile nel database. L'autenticazione dell'account utente è protetta da **BCrypt**.

***

## ✨ Funzionalità

- **Registrazione e Login** — creazione account con password hashata via BCrypt (cost factor 12)
- **Dashboard credenziali** — visualizzazione, aggiunta, modifica ed eliminazione delle credenziali salvate
- **Cifratura end-to-end** — le password delle credenziali sono cifrate con AES-256-CBC prima di essere scritte nel database
- **Derivazione chiave sicura** — la chiave AES è derivata dalla password master dell'utente con PBKDF2/HMAC-SHA256 (100.000 iterazioni)
- **IV casuale per ogni cifratura** — ogni cifratura genera un Initialization Vector unico, salvato insieme al ciphertext in Base64
- **Validazione dati** — controllo formato email, lunghezza password e campi obbligatori
- **Tema personalizzato** — interfaccia grafica con tema visivo dedicato tramite il package `GUI/Theme`

***

## 🏗️ Architettura

Il progetto segue un'architettura a livelli ispirata al pattern **Entity-Controller-DAO-Service**:

```
PasswordManager2026/
└── src/main/java/com/francesco/passwordmanager2026/
    ├── Main.java                  # Entry point — avvia il thread Swing con LoginFrame
    ├── entity/
    │   ├── AccountUtente.java     # Entità utente (email, username, password hash)
    │   └── CredenzialiAccesso.java # Entità credenziale (sito, username, password cifrata)
    ├── Controller/
    │   ├── AccountUtenteController.java   # Logica registrazione/login utente
    │   └── CredenzialiController.java     # Logica CRUD credenziali
    ├── DAO/
    │   └── ...                    # Data Access Object per operazioni PostgreSQL
    ├── Service/
    │   ├── CryptoUtil.java        # BCrypt hashing + AES-256 cifratura/decifratura
    │   ├── DBConnection.java      # Singleton connessione JDBC a PostgreSQL
    │   └── DataCheck.java         # Validazione input (email, password, ecc.)
    └── GUI/
        ├── LoginFrame.java        # Schermata di login
        ├── RegistrazioneFrame.java # Schermata di registrazione
        ├── DashboardFrame.java    # Schermata principale con lista credenziali
        ├── NuovaCredenzialeFrame.java # Form aggiunta/modifica credenziale
        ├── Dialog/                # Dialoghi di conferma e alert
        └── Theme/                 # Costanti colori e font dell'interfaccia
```

***

## 🔒 Sicurezza

| Aspetto | Tecnica | Dettagli |
|---|---|---|
| Password account | BCrypt | Salt automatico, cost factor 12 |
| Password credenziali | AES-256-CBC | Chiave derivata da PBKDF2/HMAC-SHA256 |
| Derivazione chiave | PBKDF2 | 100.000 iterazioni, salt = email utente |
| IV | SecureRandom | 16 byte casuali per ogni cifratura |
| Storage | Base64(IV + ciphertext) | IV preposto al ciphertext nel campo DB |

> **Nota:** La chiave AES non è mai persistita — viene derivata a runtime dalla password master dell'utente alla sessione di login. Senza la password master corretta, le credenziali salvate sono indecifrabili.

***

## 🛠️ Prerequisiti

- **Java 21** o superiore
- **Maven 3.6+**
- **PostgreSQL** (istanza locale o cloud, es. [Neon](https://neon.tech))

***

## ⚙️ Configurazione Database

1. Crea un database PostgreSQL e le tabelle necessarie. Esempio di schema:

```sql
CREATE TABLE account_utente (
    id        SERIAL PRIMARY KEY,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    email     VARCHAR(100) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL  -- BCrypt hash
);

CREATE TABLE credenziali_accesso (
    id             SERIAL PRIMARY KEY,
    id_utente      INTEGER REFERENCES account_utente(id) ON DELETE CASCADE,
    sito           VARCHAR(255) NOT NULL,
    username_sito  VARCHAR(255) NOT NULL,
    password       TEXT NOT NULL  -- AES-256 ciphertext in Base64
);
```

2. Aggiorna le credenziali di connessione in `DBConnection.java`:

```java
private static final String URL  = "jdbc:postgresql://<host>:<porta>/<database>";
private static final String USER = "<utente>";
private static final String PASS = "<password>";
```

***

## 🚀 Build & Run

```bash
# Clona il repository
git clone https://github.com/francesc05Cervera/PasswordManager.git
cd PasswordManager/PasswordManager2026

# Compila e pacchettizza
mvn clean package

# Avvia l'applicazione
java -jar target/PasswordManager2026-0.0.1-SNAPSHOT.jar
```

In alternativa, importa il progetto come **Maven Project** in Eclipse o IntelliJ IDEA e lancia `Main.java` direttamente dall'IDE.

***

## 📦 Dipendenze

| Libreria | Versione | Scopo |
|---|---|---|
| `org.postgresql:postgresql` | 42.7.3 | Driver JDBC per PostgreSQL |
| `org.mindrot:jbcrypt` | 0.4 | Hashing BCrypt per password account |
| `javax.crypto` (JDK) | — | AES-256-CBC per cifratura credenziali |

Gestite tramite Maven (`pom.xml`), nessuna dipendenza esterna aggiuntiva necessaria.

***

## 📖 Flusso applicativo

```
Avvio
  └─► LoginFrame
        ├─► [Nuovo utente] RegistrazioneFrame → BCrypt hash → DB
        └─► [Login] verifica BCrypt → deriva chiave AES → DashboardFrame
                                                              ├─► Visualizza credenziali (decifratura AES)
                                                              ├─► NuovaCredenzialeFrame → cifratura AES → DB
                                                              ├─► Modifica credenziale
                                                              └─► Elimina credenziale
```

***

## 👤 Autore

**Francesco Cervera** — [@francesc05Cervera](https://github.com/francesc05Cervera)

***

## 📄 Licenza

Questo progetto è distribuito a scopo didattico. Per qualsiasi utilizzo commerciale contatta l'autore.
