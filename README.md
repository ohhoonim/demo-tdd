# para 분류법 구현 프로젝트

## 도메인 모델 설계

```plantuml
@startuml

class Note {
    noteId: Long
    title: String
    content: String
}
// note쪽에서도 subject list 를 가져오네요..
// 결국 다대다 로 처리 되어야할 듯합니다. 
// JPA의 경우에도 다대일인 경우 
// 다대다로 처리해야 훨씬 성능상 이점을 가지는 경우가
// 있습니다. ...여기서는 jdbc로 작성할 거니까
// db모델링시 다대다를 해소하는 중개 테이블을 만들예정입니다. 
// dbeaver로 이동해볼께요...
 // 아 .. 우선 db를 구동하겠습니다. 
class Subject {
    subjectId: Long
    title: String
    content: String
    para: ParaEnum 
}
enum ParaEnum {
    Project("project")
    Area("area")
    Resource("resource")
    Archive("archive")
}

Note "1" -- "subjects 0..*" Subject
@enduml
```

## mockMvc : controller 테스트 ....


## testcontainers. db 테스트 (repository)

























