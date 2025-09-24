# Cafe Billing System

A modern Java Swing application for managing cafe billing operations with PDF generation and MySQL database integration.

![Cafe Billing System](Screenshot%202025-09-24%20191908.png)

## Features

- **Item Management**: Add predefined or custom items to bills
- **Automatic Calculations**: Built-in GST calculation (18%)
- **Database Integration**: MySQL database for transaction storage
- **PDF Generation**: Professional PDF bill generation with iText
- **File Management**: Direct access to PDF folder from the app
- **Modern UI**: Clean, user-friendly interface with Swing

## Tech Stack

- **Language**: Java 8+
- **Build Tool**: Maven
- **Database**: MySQL
- **PDF Library**: iText PDF 5.5.13.3
- **GUI Framework**: Java Swing

## Prerequisites

- Java 8 or higher
- Maven
- MySQL Server

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/Aish2140/CafeBillingSystem.git
cd CafeBillingSystem
```

### 2. Database Setup
Make sure MySQL is running on `localhost:3306` and create the database:

```sql
CREATE DATABASE cafe;
```

**Default MySQL Credentials:**
- Username: `root`
- Password: `1234`

> **Tip**: You can modify these credentials in `src/main/java/com/cafe/DB.java`

### 3. Build & Run

#### Option 1: Using Maven
```bash
# Build the project
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="com.cafe.Main"
```

#### Option 2: Using IDE
- Import the project into your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)
- Run the `Main.java` class

## How to Use

1. **Select Items**: Choose from predefined items or select "Custom" for custom entries
2. **Enter Details**: Input quantity and price for each item
3. **Add to Bill**: Click "Add Item" to add items to the current bill
4. **Generate Bill**: Click "Print & Save" to save transaction and generate PDF
5. **View Bills**: Use "File > Open PDF Folder" to access all generated bills

## Project Structure

```
CafeBillingSystem/
├── src/main/java/com/cafe/
│   ├── Main.java           # Application entry point
│   ├── UI.java             # User interface components
│   ├── DB.java             # Database operations
│   ├── TransactionItem.java # Item model with GST calculation
│   └── PDFGenerator.java   # PDF generation logic
├── bills/                  # Generated PDF bills (auto-created)
├── pom.xml                # Maven dependencies
└── README.md              # Project documentation
```

## PDF Bills

- **Location**: `bills/` folder in project root
- **Naming**: `bill_[transactionId]_[timestamp].pdf`
- **Content**: Itemized bill with GST calculations and totals

## Dependencies

| Library | Version | Purpose |
|---------|---------|---------|
| [iText PDF](https://mvnrepository.com/artifact/com.itextpdf/itextpdf/5.5.13.3) | 5.5.13.3 | PDF generation |
| [MySQL Connector/J](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.33) | 8.0.33 | Database connectivity |

## Customization

### Adding New Items
Edit the `itemPrices` HashMap in `UI.java`:
```java
itemPrices.put("New Item", 75.0);
```

### Changing GST Rate
Modify the `calculateGST` method in `TransactionItem.java`:
```java
private double calculateGST(double amount) {
    return amount * 0.18; // Change 0.18 to your desired rate
}
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Thanks to the iText team for the excellent PDF library
- MySQL team for the robust database system
- Java Swing for the GUI framework

---

**Star this repository if you found it helpful!**
