주택 금융 서비스 API 개발
=========================  
  
<br><br>
    
기술셋
---------
spring boot 2.1.9  
lombok 1.18.8  
spring data jpa 2.1.9 --> 반복되는 쿼리를 간단하게 사용하기 위해서 사용  
querydsl 4.2.1 --> spring data jpa로 작성하기 어려운 쿼리들을 querydsl로 작성  
opencsv 4.5 --> csv파일을 읽기 위한 라이브러리  
swagger 2.9.2 --> api를 쉽게 확인하고 테스트하실 수 있도록 swagger 사용  
  
<br><br>
  
문제해결전략  
------------

- entity 설계  

Guarantee 객체, Institution 객체 두 개의 엔티티 객체로 설계  

  <br><br>
Institution 객체(은행 객체)    

code, name 두개의 필드를 가지고 있음  
<br><br>
Guarantee 객체(금융기관 지원 금액 객체)
  
id, year, month, money 필드를 가지고 있고, Institution 객체참조를 가지고 있음(단방향관계)    
  
Guarantee 객체에서만 Institution 객체를 단방향으로 가지고 있는 것으로 충분하다고 판단  

  <br><br><br>
  
- 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API  

  1. 쉼표별로 데이터를 나누기 위해서 openCsv의 CSVReader 사용  

  2. 데이터의 첫줄을 읽어서 은행별로 headerMap을 생성

        --> 3번째 위치에 카카오뱅크가 있으면 map에 3, 카카오뱅크 삽입  
  
        --> 새로운 은행이 추가될 수 있기 때문에 고정된 클래스를 이용하지 않고 파일을 읽어서 확인하는 방법을 사용  
      
  3. 생성된 헤더 map을 통해서 데이터를 읽을 때 은행을 가져와서 객체 생성  

        --> 데이터 위치별 은행이 저장된 headerMap에서 은행을 가져온 뒤 금액과 같이 Guarantee 객체를 생성

  4. 데이터를 읽는 도중 형식을 DB에 저장할 수 있는 숫자, 문자로 바꾸기 위해 자체적으로 Parser를 개발(CsvParser클래스)  

        --> CsvParser클래스에서는 문자를 받아서 " , 1) (억원) 등 필요없는 문자를 걸러내주는 역할을 수행   

  5. 만들어진 Guarantee 객체들과, 헤더의 은행정도를 이용해 Institution 객체를 만들어서 DB에 삽입 후 결과를 돌려줌  

<br><br>
- 주택금융 공급 금융기관(은행) 목록을 출력하는 API   

  1. DB에 있는 Institution 객체들을 모두 가져와서 돌려줌  

<br><br>  

- 년도별 각 금융기관의 지원금액 합계를 출력하는 API  

  1. DB에 있는 Guarantee 객체들을 inner join fetch 을 통해서 모두 가져옴(lazy로 설정되있기 때문)  
    
  2. 가져온 Guarantee 객체들을 api 형식에 맞는 객체들로 변환 ( TotalGuaranteeByYear 와 InstitutionMoney )  
  
  3. 변환된 객체들을 돌려줌  
  
<br><br>
  
- 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API  
  
  1. 년도별 각 금융기관의 지원금액 합계를 출력하는 API에서 사용했던 TotalGuaranteeByYear객체에는 년도별 각 기관의 전체 지원금액이 나와있기 때문에 이를 재활용  
  2. TotalGuarantee 객체 리스트를 가져와서 가장 높은 금액을 가진 객체를 API 형식에 맞게 LargestGuaranteeInstitutionByYear 객체로 변환   
  3. 변환된 LargestGuaranteeInstitutionByYear 객체를 돌려줌  
  
  <br><br>  

- 전체 년도(2005~2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API  

  1. querydsl로 시작년도, 끝년도, 은행이름, min max 여부를 받아서 시작년도부터 끝년도 까지 해당 은행의 최소값 최대값을 주는 메소드 개발  
  
  2. 해당 메소드를 2005, 2016, 외환은행, 최소값 최대값 으로 호출해서 값을 받아와서 돌려줌  
  
빌드 및 실행방법
----------------
 
- jar파일을 통해서 실행하는 방식 
 
- jar 파일 다운로드 링크 ( 테스트 결과 internet explorer에서 성공적으로 다운로드 가능 )

<https://drive.google.com/open?id=1J2UkmeW6NeJDbKTglcQlAC1os0U5VTjL>  
  
  --> 해당파일을 다운로드 받은 뒤 java -jar 파일이름 을 통해서 실행 가능
  
 - swagger를 통한 api 조회  
   
 --> api를 swagger를 통해서 조회 및 테스트 해볼 수 있도록 설정   
   
 --> localhost:8000/swagger-ui.html 로 접속 후 api 조회 및 테스트 가능  
