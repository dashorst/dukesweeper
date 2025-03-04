# Dukesweeper

Dukesweeper is a simple Java-based game inspired by classic puzzle games. This project serves as a starting point for developing the game logic and mechanics.

## Project Structure

```
dukesweeper
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── dukesweeper
│   │   │           ├── App.java
│   │   │           └── Game.java
│   │   └── resources
│   └── test
│       └── java
│           └── com
│               └── dukesweeper
│                   └── AppTest.java
├── pom.xml
├── .gitignore
└── README.md
```

## Getting Started

To get started with the Dukesweeper project, follow these steps:

1. **Clone the repository**:
   ```
   git clone <repository-url>
   ```

2. **Navigate to the project directory**:
   ```
   cd dukesweeper
   ```

3. **Build the project**:
   Use Maven to build the project:
   ```
   mvn clean install
   ```

4. **Run the application**:
   You can run the application using:
   ```
   mvn exec:java -Dexec.mainClass="com.dukesweeper.App"
   ```

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue for any suggestions or improvements.

## License

This project is licensed under the MIT License - see the LICENSE file for details.