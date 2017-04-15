package cn.gilight.product.common.teacher.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.product.common.teacher.dao.TeacherDao;
import cn.gilight.product.common.teacher.entity.Teacher;
import cn.gilight.product.common.teacher.service.TeacherService;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
	
	@Autowired
	private TeacherDao teacherDao;
	
	@Override
	public Teacher getTeacherInfo(String teacherNo) {
		return teacherDao.getTeacherInfo(teacherNo);
	}

	@Override
	public Teacher getInstructorInfo(String classId, String year, String term) {
		return teacherDao.getInstructorInfo(classId, year, term);
	}

	@Override
	public Teacher getInstructorInfoByThisYearTerm(String classId) {
		String [] yearTerm=EduUtils.getSchoolYearTerm(new Date());
		return teacherDao.getInstructorInfo(classId, yearTerm[0], yearTerm[1]);
	}

	@Override
	public boolean isInDeptForTeacher(String teaId,String depts) {
		return teacherDao.isInDeptForTeacher(teaId, depts);
	}

	@Override
	public List<Teacher> getTeasInSchoolNotInUser() {
		return teacherDao.getTeasInSchoolNotInUser();
	}

}
