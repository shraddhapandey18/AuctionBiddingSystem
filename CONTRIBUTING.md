# Contributing to AuctionBiddingSystem

## Overview

This is a student project created for an Advanced Object-Oriented Programming course. While this is primarily an academic submission, contributions in the form of bug reports, documentation improvements, and code refinements are welcome.

## How to Contribute

### Reporting Issues

If you find a bug or issue:

1. Check if the issue already exists in the Issues tab
2. Create a detailed bug report including:
   - Steps to reproduce
   - Expected behavior
   - Actual behavior
   - Environment (Java version, OS)

### Code Contributions

While this is primarily a course project, improvements are welcome:

1. **Fork** the repository
2. **Create a branch** for your feature (`git checkout -b feature/improvement`)
3. **Make your changes** following the code style
4. **Test thoroughly** to ensure functionality isn't broken
5. **Commit** with clear messages
6. **Push** to your branch
7. **Create a Pull Request** with a detailed description

### Code Style Guidelines

- **Naming Conventions**
  - Classes: PascalCase (e.g., `AuctionManager`)
  - Methods/Variables: camelCase (e.g., `getCurrentPrice()`)
  - Constants: UPPER_SNAKE_CASE

- **Formatting**
  - Use 4-space indentation
  - One class per file
  - Keep line length reasonable (80-120 chars)

- **Documentation**
  - Add Javadoc comments for public methods
  - Comment complex logic with explanations
  - Keep comments accurate and up-to-date

- **Encapsulation**
  - Keep all fields private
  - Provide getters where necessary
  - Avoid exposing internal collections directly

### Example Improvements

Potential areas for contribution:

1. **Enhanced Features**
   - Bidding strategies
   - Auction categories
   - Advanced search
   - User authentication

2. **Code Quality**
   - Additional unit tests
   - Performance optimizations
   - Code refactoring

3. **Documentation**
   - API documentation
   - Architecture diagrams
   - Tutorial videos

4. **Testing**
   - Edge case handling
   - Thread safety verification
   - File I/O error scenarios

## Development Setup

### Prerequisites
- JDK 8 or higher
- Git
- Your favorite Java IDE (optional)

### Quick Start

```bash
# Clone the repository
git clone https://github.com/yourusername/AuctionBiddingSystem.git
cd AuctionBiddingSystem

# Compile
javac -d out src\*.java src\model\*.java src\auction\*.java \
  src\observer\*.java src\core\*.java src\storage\*.java src\exceptions\*.java

# Run demo
echo "8" | java -cp out Main
```

## Testing Your Changes

Before submitting, ensure:

1. ✅ Project compiles without errors
2. ✅ Demo mode runs without exceptions
3. ✅ All menu options work correctly
4. ✅ File I/O operations complete successfully
5. ✅ No new warnings or deprecated API usage

### Test Checklist

```bash
# Compile with warnings enabled
javac -d out -Xlint src\*.java src\model\*.java src\auction\*.java \
  src\observer\*.java src\core\*.java src\storage\*.java src\exceptions\*.java

# Run demo test
echo "8" | java -cp out Main

# Test menu navigation
@"
1
2
3
...
9
"@ | java -cp out Main
```

## Commit Message Guidelines

Use clear, descriptive commit messages:

- ✅ `Fix: Handle null bids in auction winner determination`
- ✅ `Feature: Add auction search by item name`
- ✅ `Docs: Update README with threading explanation`
- ❌ `fix bug` (too vague)
- ❌ `asdf` (meaningless)

## Pull Request Process

1. Update documentation for new features
2. Add tests for new functionality
3. Ensure no breaking changes to existing API
4. Provide clear PR description explaining the changes
5. Be responsive to code review feedback

## Questions?

- Check the README.md for usage information
- Review ASSIGNMENT.md for requirements and design decisions
- Look at existing code comments for architectural insights

## Academic Integrity

Since this is a student project:

- Changes should enhance learning outcomes
- Maintain the educational purpose of the project
- Credit all sources and inspiration
- Respect the original assignment requirements

## License

This project is provided for educational purposes. Please maintain appropriate attribution.

---

Thank you for your interest in this project! Happy coding! 🚀
