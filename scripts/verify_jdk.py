import os
import subprocess

print(f"JAVA_HOME: {os.environ.get('JAVA_HOME')}")
if os.environ.get('JAVA_HOME') and os.path.isdir(os.environ.get('JAVA_HOME')):
    print("JAVA_HOME Status: VALID")
else:
    print("JAVA_HOME Status: INVALID")

print("\nJava Version:")
try:
    res = subprocess.run(["java", "-version"], capture_output=True, text=True)
    print(res.stdout if res.stdout else res.stderr)
except Exception as e:
    print(f"Error: {e}")
