# Fix 404 Error in IntelliJ with Tomcat 10.1.44

## The Problem
You're getting a 404 error even though the setup looks correct. This is usually due to IntelliJ deployment configuration issues.

## Step-by-Step Fix

### 1. Check Your Current URL
Make sure you're accessing the correct URL:
- ✅ Correct: `http://localhost:8080/SpringMvcHelloWorld/`
- ❌ Wrong: `http://localhost:8080/`

### 2. IntelliJ Run Configuration Setup

1. **Open Run Configurations**
   - Go to `Run` → `Edit Configurations...`
   - Or click the dropdown next to the run button and select `Edit Configurations...`

2. **Create/Edit Tomcat Configuration**
   - Click `+` → `Tomcat Server` → `Local`
   - Name: `SpringMvcHelloWorld`

3. **Server Tab**
   - Application server: Select your Tomcat 10.1.44 installation
   - HTTP port: `8080`
   - JRE: Use project default

4. **Deployment Tab** (This is the critical part!)
   - Click `+` → `Artifact...`
   - Select `SpringMvcHelloWorld:war exploded` (NOT the .war file)
   - Application context: `/SpringMvcHelloWorld`
   - Click `Apply`

5. **Before Launch**
   - Click `+` → `Build Artifacts`
   - Select `SpringMvcHelloWorld:war exploded`
   - Click `Apply` → `OK`

### 3. Common Mistakes to Avoid

❌ **Wrong Artifact Type**
- Don't use `SpringMvcHelloWorld.war`
- Use `SpringMvcHelloWorld:war exploded`

❌ **Wrong Application Context**
- Don't use `/` or empty
- Use `/SpringMvcHelloWorld`

❌ **Wrong URL**
- Don't access `http://localhost:8080/`
- Access `http://localhost:8080/SpringMvcHelloWorld/`

### 4. Test URLs (in order)

1. `http://localhost:8080/SpringMvcHelloWorld/` - Home page
2. `http://localhost:8080/SpringMvcHelloWorld/employee/register` - Registration form
3. `http://localhost:8080/SpringMvcHelloWorld/api/employee/health` - API health check

### 5. Check IntelliJ Console

Look for these logs in the IntelliJ console:
```
INFO: Starting Servlet engine: [Apache Tomcat/10.1.44]
INFO: Initializing Spring DispatcherServlet 'dispatcher'
INFO: Completed initialization in XXX ms
```

If you see errors about `jakarta.servlet.http.HttpServlet`, the Tomcat version is wrong.

### 6. If Still Not Working

1. **Clean and Rebuild**
   - `Build` → `Clean`
   - `Build` → `Rebuild Project`

2. **Check Tomcat Version**
   - Make sure you're using Tomcat 10.1.44, not 9.x or 8.x

3. **Restart IntelliJ**
   - Sometimes IntelliJ needs a restart after configuration changes

## Quick Verification

Run this in terminal to verify the WAR file is correct:
```bash
cd /Users/abiralkhanal/Documents/SpringMvcHelloWorld
mvn clean package
ls -la target/SpringMvcHelloWorld.war
```

The file should exist and be several MB in size.

