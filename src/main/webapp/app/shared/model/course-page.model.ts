import { ISection } from 'app/shared/model/section.model';

export interface ICoursePage {
  id?: number;
  rating?: number;
  sections?: ISection[];
}

export class CoursePage implements ICoursePage {
  constructor(public id?: number, public rating?: number, public sections?: ISection[]) {}
}
