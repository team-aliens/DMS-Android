package team.aliens.dms.android.data.student.model

import team.aliens.dms.android.network.student.model.FetchStudentsResponse

data class Student(
    val id: String,
    val name: String,
    val gradeClassNumber: String,
    val profileImageUrl: String,
)

fun FetchStudentsResponse.toModel(): List<Student> =
    this.students.map(FetchStudentsResponse.StudentResponse::toModel)

fun FetchStudentsResponse.StudentResponse.toModel(): Student = Student(
    id = this.id,
    name = this.name,
    gradeClassNumber = this.gradeClassNumber,
    profileImageUrl = this.profileImageUrl,
)
