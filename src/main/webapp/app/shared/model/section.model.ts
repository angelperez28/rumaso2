import { IProfessor } from 'app/shared/model/professor.model';
import { IGrades } from 'app/shared/model/grades.model';
import { ICoursePage } from 'app/shared/model/course-page.model';

export interface ISection {
  id?: number;
  courseName?: string;
  semester?: string;
  year?: number;
  department?: string;
  section?: string;
  professor?: IProfessor;
  grades?: IGrades;
  coursePages?: ICoursePage[];
}

export class Section implements ISection {
  constructor(
    public id?: number,
    public courseName?: string,
    public semester?: string,
    public year?: number,
    public department?: string,
    public section?: string,
    public professor?: IProfessor,
    public grades?: IGrades,
    public coursePages?: ICoursePage[]
  ) {}
}
