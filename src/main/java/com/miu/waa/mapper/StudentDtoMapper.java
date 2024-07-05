package com.miu.waa.mapper;
import com.miu.waa.dto.request.StudentCreateDto;
import com.miu.waa.dto.response.StudentResponseDto;
import com.miu.waa.entities.Student;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentDtoMapper {
    StudentDtoMapper dtoMapper =
            Mappers.getMapper(StudentDtoMapper.class);

    StudentResponseDto studentToStudentResponseDto(Student student);
    Student studentCreateDtoToStudent(StudentCreateDto student);
}
