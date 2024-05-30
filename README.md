# Y_L_Project
양지민, 임시현 졸업작품 프로젝트 진행

https://blog.naver.com/PostView.nhn?blogId=saegot&logNo=222453762728

xampp 다운후 아파치 mysql filezila 설치후
작업관리자 - 서비스 mysql80 중지
mysql port 오류시 Config-my.ini 에서 port관련부분 3306으로 고정
Apache포트번호 또한 Config - 첫번째 항목 들어가서 기본값 80이라 적혀있는부분 8080으로 변경 
이러면 보통 오류는 전부 해결 

php 파일은 로컬 c - xampp폴더 - htdocs폴더안에 추가하거나 폴더안에 따로 폴더를 생성하여 그 폴더에 php 파일 작성 

PHPADMIN 웹페이지 접속시 비밀번호 입력 X + 비밀번호가 없는게 기본값이므로 변경 
-> C:\xampp\phpMyAdmin\config.inc.php 열어서 포트번호 db아이디 비번 수정

xampp 첫실행시 로그창에 port 충돌 oracle.exe 발생시 아래와 같은 작업 수행
1. Window키 + R키 -> cmd 입력 명령 프롬프트창으로 이동합니다.

2. sqlplus 입력

3. sys계정으로 로그인 안될시 sys as sysdba

4. 현재 오라클에 설정된 포트번호 조회

SELECT DBMS_XDB.GETHTTPPORT() FROM DUAL;

5. 오라클 포트번호를 수정합니다.

EXEC DBMS_XDB.SETHTTPPORT(9090);

