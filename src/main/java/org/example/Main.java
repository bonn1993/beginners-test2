package org.example;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    String input;
    String addName;
    int addScore;
    int selectMenu;
    List<Student> studentList = new ArrayList<>();

    // メニュー選択
    while (true) {
      System.out.println("\n1. 学生を追加");
      System.out.println("2. 学生を削除");
      System.out.println("3. 点数を更新");
      System.out.println("4. 平均点を計算");
      System.out.println("5. 全学生の情報を表示");
      System.out.println("6. 終了");
      System.out.print("選択してください: ");

      // 7~9や数字以外を入力した場合のエラー処理
      try {
        selectMenu = scanner.nextInt();
        if (selectMenu < 1 || selectMenu > 6) {
          System.out.println("無効な文字が入力されています");
          continue;
        }
      } catch (InputMismatchException e) {
        System.out.println("無効な文字が入力されています");
        scanner.next();
        continue;
      }

      //各メニュー
      switch (selectMenu) {
        //学生を追加
        case 1:
          System.out.print("学生の名前を入力してください: ");
          addName = scanner.next();
          if (addName.matches("^[0-9]+$")) {
            System.out.println("無効な文字が入力されています");
            continue;
          } else {
            System.out.print(addName + "の点数を入力してください: ");
            input = scanner.next();
            try {
              addScore = Integer.parseInt(input);
              if (addScore < 0 || addScore > 100) {
                System.out.println("点数は0～100の範囲で入力してください");
                continue;
              } else {
              }
            } catch (NumberFormatException e) {
              System.out.println("無効な文字が入力されています");
              continue;
            }
            Student addStudent = new Student(addName, addScore);
            studentList.add(addStudent);
            break;
          }

          //学生を削除
        case 2:
          System.out.print("学生の名前を入力してください: ");
          input = scanner.next();
          String searchStudentName = input;
          Optional<Student> studentToRemoveOptional = studentList.stream()
              .filter(student -> student.getName().equals(searchStudentName))
              .findFirst();

          if (studentToRemoveOptional.isPresent()) {
            Student studentToRemove = studentToRemoveOptional.get();
            System.out.println(searchStudentName + "のデータを削除します");
            studentList.remove(studentToRemove);
          } else {
            System.out.println("該当する学生データが存在しません");
          }
          break;

          //点数を更新
        case 3:
          System.out.print("点数を更新したい学生の名前を入力してください: ");
          input = scanner.next();
          searchStudentName = input;
          Optional<Student> studentToUpdateOptional = studentList.stream()
              .filter(student -> student.getName().equals(searchStudentName))
              .findFirst();

          if (studentToUpdateOptional.isPresent()) {
            Student studentToUpdate = studentToUpdateOptional.get();
            System.out.print("新しい点数を入力してください: ");
            input = scanner.next();
            try {
              addScore = Integer.parseInt(input);
              if (addScore < 0 || addScore > 100) {
                System.out.println("点数は0～100の範囲で入力してください");
                continue;
              } else {
                studentToUpdate.setScore(addScore);
                System.out.println(studentToUpdate.getName() + "の点数が[" + studentToUpdate.getScore() + "]に更新されました");
              }
            } catch (NumberFormatException e) {
              System.out.println("無効な文字が入力されています");
              continue;
            }
          } else {
            System.out.println("該当する学生データが存在しません");
          }
          break;

          //平均点を計算
        case 4:
          OptionalDouble averageScore = studentList.stream()
              .mapToInt(Student::getScore)
              .average();

          averageScore.ifPresent(average -> System.out.println("平均点: " + average + "点"));
          break;

          //全学生の情報を表示
        case 5:
          System.out.println("学生一覧:");
          for (Student student : studentList) {
            System.out.println(student);
          }
          continue;

          //終了
        case 6:
          System.out.println("プログラムを終了します");
          scanner.close();
          return;
      }
    }
  }
}

