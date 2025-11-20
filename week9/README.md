# Android Calculator App Testing (Using Firebase Test Lab)

## 📱 Project Overview

This  tutorial guides you through creating a simple Android calculator application and testing it using Firebase Test Lab. The goal is to show you the complete process from building the basic Android app to running automated tests on cloud-based devices.


## Create Calculator User Interface

We can use a simple Linear layout to represent a hierarchy of View and ViewGroup objects


### Complete activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    android:background="#f0f0f0">

    <!-- 
        Display Area 
        Shows the current number and calculation results
    -->
    <TextView
        android:id="@+id/display"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:background="#ffffff"
        android:gravity="center_vertical|end"
        android:padding="16dp"
        android:text="0"
        android:textSize="32sp"
        android:textColor="#333333"
        android:elevation="4dp" />

    <!-- 
        Calculator Button Grid
        Uses GridLayout for consistent button arrangement
    -->
    <GridLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:columnCount="4"
        android:rowCount="5">

        <!-- Row 1: Clear and Basic Operators -->
        <Button
            android:id="@+id/btn_clear"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:backgroundTint="#ff6b6b"
            android:text="C"
            android:textSize="24sp"
            android:textColor="#ffffff" />

        <Button
            android:id="@+id/btn_divide"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:backgroundTint="#4ecdc4"
            android:text="÷"
            android:textSize="24sp"
            android:textColor="#ffffff" />

        <Button
            android:id="@+id/btn_multiply"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:backgroundTint="#4ecdc4"
            android:text="×"
            android:textSize="24sp"
            android:textColor="#ffffff" />

        <Button
            android:id="@+id/btn_subtract"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:backgroundTint="#4ecdc4"
            android:text="-"
            android:textSize="24sp"
            android:textColor="#ffffff" />

        <!-- Row 2: Numbers 7-9 and Add Operator -->
        <Button
            android:id="@+id/btn_7"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="7"
            android:textSize="24sp" />

        <Button
            android:id="@+id/btn_8"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="8"
            android:textSize="24sp" />

        <Button
            android:id="@+id/btn_9"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="9"
            android:textSize="24sp" />

        <Button
            android:id="@+id/btn_add"
            android:layout_width="0dp"
            android:layout_height="160dp"
            android:layout_columnWeight="1"
            android:layout_rowSpan="2"
            android:layout_margin="4dp"
            android:backgroundTint="#4ecdc4"
            android:text="+"
            android:textSize="24sp"
            android:textColor="#ffffff" />

        <!-- Row 3: Numbers 4-6 -->
        <Button
            android:id="@+id/btn_4"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="4"
            android:textSize="24sp" />

        <Button
            android:id="@+id/btn_5"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="5"
            android:textSize="24sp" />

        <Button
            android:id="@+id/btn_6"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="6"
            android:textSize="24sp" />

        <!-- Row 4: Numbers 1-3 and Equals -->
        <Button
            android:id="@+id/btn_1"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="1"
            android:textSize="24sp" />

        <Button
            android:id="@+id/btn_2"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="2"
            android:textSize="24sp" />

        <Button
            android:id="@+id/btn_3"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="3"
            android:textSize="24sp" />

        <Button
            android:id="@+id/btn_equals"
            android:layout_width="0dp"
            android:layout_height="160dp"
            android:layout_columnWeight="1"
            android:layout_rowSpan="2"
            android:layout_margin="4dp"
            android:backgroundTint="#45b7d1"
            android:text="="
            android:textSize="24sp"
            android:textColor="#ffffff" />

        <!-- Row 5: Zero and Decimal -->
        <Button
            android:id="@+id/btn_0"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnSpan="2"
            android:layout_columnWeight="2"
            android:layout_margin="4dp"
            android:text="0"
            android:textSize="24sp" />

        <Button
            android:id="@+id/btn_decimal"
            android:layout_width="0dp"
            android:layout_height="80dp"
            android:layout_columnWeight="1"
            android:layout_margin="4dp"
            android:text="."
            android:textSize="24sp" />

    </GridLayout>
</LinearLayout>
```


## Implement Calculator Logic

The calculator follows a simple state-based architecture:
- **State Variables**: Track current number, operator, and previous values
- **Event Handlers**: Respond to button clicks
- **Business Logic**: Perform calculations and update display

### MainActivity.kt

```kotlin

class MainActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var display: TextView
    
    // Calculator State
    private var currentNumber = ""          // Currently entered number
    private var currentOperator = ""        // Selected operator (+, -, ×, ÷)
    private var firstNumber = 0.0           // First number in operation
    private var isNewCalculation = true     // Track if starting new calculation

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_main)
        display = findViewById(R.id.display)
        setupCalculator()
    }

    /**
     * Configures all calculator button click listeners
     * Separated for better organization and readability
     */
    private fun setupCalculator() {
        setupNumberButtons()
        setupOperatorButtons()
        setupDecimalButton()
        setupEqualsButton()
        setupClearButton()
    }

    /**
     * Sets up listeners for number buttons (0-9)
     * Handles digit input and display updates
     */
    private fun setupNumberButtons() {
        // List of all number button IDs
        val numberButtonIds = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        )

        // Set click listener for each number button
        numberButtonIds.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { button ->
                // Get the number from button text
                val number = (button as Button).text.toString()
                
                // Handle number input based on current state
                if (isNewCalculation) {
                    // Starting new number - replace display
                    currentNumber = number
                    isNewCalculation = false
                } else {
                    // Appending to existing number
                    currentNumber += number
                }
                
                // Update display with current number
                updateDisplay()
            }
        }
    }

    /**
     * Sets up listeners for operator buttons (+, -, ×, ÷)
     * Stores the first number and selected operator
     */
    private fun setupOperatorButtons() {
        // List of all operator button IDs
        val operatorButtonIds = listOf(
            R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide
        )

        // Set click listener for each operator button
        operatorButtonIds.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { button ->
                // Only set operator if we have a current number
                if (currentNumber.isNotEmpty()) {
                    // Store first number and operator
                    firstNumber = currentNumber.toDouble()
                    currentOperator = (button as Button).text.toString()
                    
                    // Reset current number for second operand
                    currentNumber = ""
                    
                    // Update display (clears for next number)
                    updateDisplay()
                }
            }
        }
    }

    /**
     * Sets up listener for decimal point button
     * Ensures only one decimal point per number
     */
    private fun setupDecimalButton() {
        findViewById<Button>(R.id.btn_decimal).setOnClickListener {
            if (isNewCalculation) {
                // Starting new number with decimal
                currentNumber = "0."
                isNewCalculation = false
                display.text = currentNumber
            } else if (!currentNumber.contains(".")) {
                // Add decimal if not already present
                currentNumber += "."
                display.text = currentNumber
            }
            // If decimal already exists, do nothing
        }
    }

    /**
     * Sets up listener for equals button
     * Performs calculation and displays result
     */
    private fun setupEqualsButton() {
        findViewById<Button>(R.id.btn_equals).setOnClickListener {
            // Only calculate if we have both operator and second number
            if (currentOperator.isNotEmpty() && currentNumber.isNotEmpty()) {
                // Convert second number from string to double
                val secondNumber = currentNumber.toDouble()
                
                // Perform the calculation
                val result = calculate(firstNumber, secondNumber, currentOperator)
                
                // Display formatted result
                display.text = formatResult(result)
                
                // Store result for potential continued calculations
                currentNumber = result.toString()
                currentOperator = ""
                isNewCalculation = true
            }
        }
    }

    /**
     * Sets up listener for clear button
     * Resets calculator to initial state
     */
    private fun setupClearButton() {
        findViewById<Button>(R.id.btn_clear).setOnClickListener {
            // Reset all state variables
            currentNumber = ""
            currentOperator = ""
            firstNumber = 0.0
            isNewCalculation = true
            
            // Reset display to initial state
            display.text = "0"
        }
    }

    /**
     * Performs mathematical operations based on operator
     * 
     * @param num1 First operand
     * @param num2 Second operand
     * @param operator Mathematical operator (+, -, ×, ÷)
     * @return Result of the operation
     */
    private fun calculate(num1: Double, num2: Double, operator: String): Double {
        return when (operator) {
            "+" -> num1 + num2      // Addition
            "-" -> num1 - num2      // Subtraction
            "×" -> num1 * num2      // Multiplication
            "÷" -> {                // Division with zero check
                if (num2 != 0.0) {
                    num1 / num2
                } else {
                    0.0  // Handle division by zero gracefully
                }
            }
            else -> num2  // Fallback - return second number
        }
    }

    /**
     * Formats calculation result for display
     * Removes unnecessary decimal places for whole numbers
     * 
     * @param result Raw calculation result
     * @return Formatted string for display
     */
    private fun formatResult(result: Double): String {
        return if (result == result.toLong().toDouble()) {
            // Result is a whole number - remove decimal
            result.toLong().toString()
        } else {
            // Result has decimal places - keep as is
            result.toString()
        }
    }

    /**
     * Updates the display with current number
     * Only updates if there's a current number to display
     */
    private fun updateDisplay() {
        if (currentNumber.isNotEmpty()) {
            display.text = currentNumber
        }
    }
}
```


## 🧪 Write Espresso Tests

### Understanding Espresso Testing

Espresso is Android's UI testing framework that:
- Simulates user interactions (clicks, typing)
- Verifies UI state and content
- Runs on actual devices or emulators
- Provides synchronization with app events

### Test File Location and Structure

Create the test file in: `app/src/androidTest/java/my/edu/nottingham/firebasetestlab_demo/CalculatorTest.kt`

### Complete CalculatorTest.kt

```kotlin

@RunWith(AndroidJUnit4::class)
class CalculatorTest {

    // Manages the activity lifecycle during testing
    private lateinit var scenario: ActivityScenario<MainActivity>

    /**
     * Setup method runs before each test
     * Launches the MainActivity to prepare for testing
     */
    @Before
    fun setup() {
        // Launch the activity before each test case
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    /**
     * Cleanup method runs after each test
     * Closes the activity to ensure test isolation
     */
    @After
    fun tearDown() {
        // Close the activity after each test case
        scenario.close()
    }

    /**
     * Test basic addition functionality
     * Verifies: 2 + 3 = 5
     */
    @Test
    fun testAddition() {
        // Step 1: Enter first number (2)
        onView(withId(R.id.btn_2)).perform(click())
        
        // Step 2: Select addition operator
        onView(withId(R.id.btn_add)).perform(click())
        
        // Step 3: Enter second number (3)
        onView(withId(R.id.btn_3)).perform(click())
        
        // Step 4: Execute calculation
        onView(withId(R.id.btn_equals)).perform(click())
        
        // Step 5: Verify result is correct
        onView(withId(R.id.display)).check(matches(withText("5")))
    }

    /**
     * Test subtraction functionality
     * Verifies: 8 - 3 = 5
     */
    @Test
    fun testSubtraction() {
        // Perform subtraction: 8 - 3
        onView(withId(R.id.btn_8)).perform(click())
        onView(withId(R.id.btn_subtract)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())
        
        // Verify result
        onView(withId(R.id.display)).check(matches(withText("5")))
    }

    /**
     * Test multiplication functionality
     * Verifies: 4 × 3 = 12
     */
    @Test
    fun testMultiplication() {
        // Perform multiplication: 4 × 3
        onView(withId(R.id.btn_4)).perform(click())
        onView(withId(R.id.btn_multiply)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())
        
        // Verify result
        onView(withId(R.id.display)).check(matches(withText("12")))
    }

    /**
     * Test division functionality
     * Verifies: 15 ÷ 3 = 5
     */
    @Test
    fun testDivision() {
        // Perform division: 15 ÷ 3
        onView(withId(R.id.btn_1)).perform(click())
        onView(withId(R.id.btn_5)).perform(click())
        onView(withId(R.id.btn_divide)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())
        
        // Verify result
        onView(withId(R.id.display)).check(matches(withText("5")))
    }

    /**
     * Test clear button functionality
     * Verifies display resets to 0 after clear
     */
    @Test
    fun testClearButton() {
        // Enter a number and then clear
        onView(withId(R.id.btn_5)).perform(click())
        onView(withId(R.id.btn_clear)).perform(click())
        
        // Verify display is reset to initial state
        onView(withId(R.id.display)).check(matches(withText("0")))
    }

    /**
     * Test decimal number operations
     * Verifies: 2.5 + 1.5 = 4
     */
    @Test
    fun testDecimalOperations() {
        // Enter 2.5
        onView(withId(R.id.btn_2)).perform(click())
        onView(withId(R.id.btn_decimal)).perform(click())
        onView(withId(R.id.btn_5)).perform(click())
        
        // Select addition
        onView(withId(R.id.btn_add)).perform(click())
        
        // Enter 1.5
        onView(withId(R.id.btn_1)).perform(click())
        onView(withId(R.id.btn_decimal)).perform(click())
        onView(withId(R.id.btn_5)).perform(click())
        
        // Execute calculation
        onView(withId(R.id.btn_equals)).perform(click())
        
        // Verify result
        onView(withId(R.id.display)).check(matches(withText("4")))
    }

    /**
     * Test multiple digit numbers
     * Verifies: 123 + 456 = 579
     */
    @Test
    fun testMultipleDigitNumbers() {
        // Enter 123
        onView(withId(R.id.btn_1)).perform(click())
        onView(withId(R.id.btn_2)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        
        // Select addition
        onView(withId(R.id.btn_add)).perform(click())
        
        // Enter 456
        onView(withId(R.id.btn_4)).perform(click())
        onView(withId(R.id.btn_5)).perform(click())
        onView(withId(R.id.btn_6)).perform(click())
        
        // Execute calculation
        onView(withId(R.id.btn_equals)).perform(click())
        
        // Verify result
        onView(withId(R.id.display)).check(matches(withText("579")))
    }

    /**
     * Test chained operations
     * Verifies: (5 + 3) × 2 = 16
     */
    @Test
    fun testChainedOperations() {
        // First operation: 5 + 3 = 8
        onView(withId(R.id.btn_5)).perform(click())
        onView(withId(R.id.btn_add)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())
        
        // Second operation: 8 × 2 = 16
        onView(withId(R.id.btn_multiply)).perform(click())
        onView(withId(R.id.btn_2)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())
        
        // Verify final result
        onView(withId(R.id.display)).check(matches(withText("16")))
    }

    /**
     * Test division by zero handling
     * Verifies graceful handling of divide by zero
     */
    @Test
    fun testDivisionByZero() {
        // Attempt division by zero: 5 ÷ 0
        onView(withId(R.id.btn_5)).perform(click())
        onView(withId(R.id.btn_divide)).perform(click())
        onView(withId(R.id.btn_0)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())
        
        // Verify app doesn't crash and shows result
        onView(withId(R.id.display)).check(matches(withText("0")))
    }
}
```

### Test Structure

1. **Test Annotations**:
   - `@RunWith(AndroidJUnit4.class)`: Specifies test runner
   - `@Before`: Setup method before each test
   - `@After`: Cleanup method after each test
   - `@Test`: Marks individual test cases

2. **Espresso Components**:
   - `onView()`: Finds UI elements
   - `withId()`: Locates elements by resource ID
   - `perform(click())`: Simulates user clicks
   - `check(matches())`: Verifies expected state

3. **Test Scenarios**:
   - Basic arithmetic operations
   - Decimal number handling
   - Multiple digit inputs
   - Chained calculations
   - Error case handling

## 🔥 Firebase Test Lab Setup

### Step 1: Set up Firebase Project

1. **Access Firebase Console**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Sign in with your Google account

2. **Create New Project**
   - Click "Add project"
   - Enter project name: "CalculatorTestLab"
   - Follow the setup wizard
   - Choose whether to enable Google Analytics (optional for testing)

3. **Enable Test Lab**
   - In your Firebase project, navigate to "Quality" section
   - Click on "Test Lab"
   - Review the overview and features

### Step 2: Install Google Cloud SDK

#### macOS Installation:
```bash
# Install using Homebrew
brew install google-cloud-sdk

# Add to your shell profile
echo 'source "$(brew --prefix)/Caskroom/google-cloud-sdk/latest/google-cloud-sdk/path.bash.inc"' >> ~/.bash_profile
echo 'source "$(brew --prefix)/Caskroom/google-cloud-sdk/latest/google-cloud-sdk/completion.bash.inc"' >> ~/.bash_profile

# Reload profile
source ~/.bash_profile
```

#### Windows Installation:
1. Download from [Google Cloud SDK](https://cloud.google.com/sdk/docs/install)
2. Run the installer
3. Open Command Prompt as administrator

#### Linux Installation:
```bash
# Download and install
curl https://sdk.cloud.google.com | bash

# Restart shell
exec -l $SHELL
```

### Step 3: Initialize and Authenticate

```bash
# Initialize gcloud
gcloud init

# Authenticate with your Google account
gcloud auth login

# Set your project (replace with your actual project ID)
gcloud config set project your-firebase-project-id

# Verify setup
gcloud auth list
gcloud config list
```

### Step 4: Build APK Files

#### Understanding APK Types:
- **App APK**: Your main application
- **Test APK**: Contains your Espresso tests

#### Build Commands:
```bash
# Navigate to your project directory
cd path/to/your/android/project

# Build the main application APK (debug version)
./gradlew assembleDebug

# Build the test APK (contains your instrumented tests)
./gradlew assembleDebugAndroidTest

# Verify APKs were created
ls app/build/outputs/apk/debug/
ls app/build/outputs/apk/androidTest/debug/
```

### Step 5: Run Tests on Firebase Test Lab

#### Basic Test Execution
```bash
gcloud firebase test android run \
  --type instrumentation \
  --app app/build/outputs/apk/debug/app-debug.apk \
  --test app/build/outputs/app
  ```

  ### Choose device for  Test Execution

    - https://firebase.google.com/docs/test-lab/android/available-testing-devices

  ```bash
  gcloud firebase test android run \
  --type instrumentation \
  --app app/build/outputs/apk/debug/app-debug.apk \
  --test app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk \
  --device model=Pixel2,version=28,locale=en,orientation=portrait \
  --timeout 5m
  ```

  ### Lits of devices
  ```bash
  gcloud firebase test android models list
  ```