#Parallel computing course work
Program for building the inverted index of .txt files. Single-mode and multithreading-mode are available.

## Prerequisites
JDK 11 and JRE 11

IntelliJ IDEA  (not mandatory)

## Instalation and running
**1] Command line option**
1) git init
2) git clone https://github.com/kopylash-roman/course_work_parallel_computing.git
3) cd course_work_parallel_computing
4) javac -sourcepath ./src -d bin src/main/java/com/kpi/coursework/IndexBuilderRunner.java src/main/java/com/kpi/coursework/BuildThread.java src/main/java/com/kpi/coursework/IndexUserThread.java src/main/java/com/kpi/coursework/InvertedIndexBuilder.java src/main/java/com/kpi/coursework/InvertedIndexHelper.java
5) java -classpath ./bin com.kpi.coursework.IndexBuilderRunner
6) Follow the instructions

**2] IntelliJ IDEA option**
1) Clone Repository
2) Open downloaded folder in IntelliJ IDEA
3) Build the project (Ctrl + F9)
4) Run the project (Shift + F10)
5) Follow the instructions

## Usage
Put your files into folders inside the *text_file* folder.

*text_file* folder should mandatory contain other folders with .txt files!

## Contacts
Roman Kopylash. KPI, IASA (DA82)