all:
	javac App.java
	java App
	powershell -Command "Remove-Item *.class"
