# Cafe Billing System

A simple Java Swing application for managing cafe billing, with PDF bill generation and MySQL database integration.

## Features
- Add items to a bill with quantity and price
- Predefined and custom items
- Automatic GST calculation (18%)
- Save transactions and items to MySQL database
- Generate professional PDF bills
- Open PDF folder directly from the app
- Clean, modern UI

## Requirements
- Java 8 or higher
- Maven
- MySQL Server

## Setup
1. **Clone the repository**
   ```sh
   git clone <your-repo-url>
   cd CafeBillingSystem[1]
   ```
2. **Configure MySQL**
   - Make sure MySQL is running on `localhost:3306`
   - Create a database named `cafe`:
     ```sql
     CREATE DATABASE cafe;
     ```
   - The default MySQL credentials are:
     - Username: `root`
     - Password: `1234`
   - You can change these in `src/main/java/com/cafe/DB.java` if needed.

3. **Build the project**
   ```sh
   mvn clean package
   ```

4. **Run the application**
   ```sh
   mvn exec:java -Dexec.mainClass="com.cafe.Main"
   ```

## Usage
- Select an item from the dropdown or choose "Custom" to enter your own
- Enter the quantity and price
- Click **Add Item** to add to the bill
- Click **Print & Save** to save the transaction and generate a PDF
- Use **File > Open PDF Folder** to view all generated bills

## PDF Bills
- All PDF bills are saved in the `bills` folder in your project root
- Each bill is named as `bill_[transactionId]_[timestamp].pdf`

## Dependencies
- [iText PDF 5.5.13.3](https://mvnrepository.com/artifact/com.itextpdf/itextpdf/5.5.13.3)
- [MySQL Connector/J 8.0.33](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.33)

## Customization
- To add more predefined items, edit the `itemPrices` map in `UI.java`
- To change GST rate, edit the `calculateGST` method in `TransactionItem.java`

## Screenshots
_Add screenshots here if desired._

## License
MIT 