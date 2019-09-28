import { ISection } from 'app/shared/model/section.model';

export interface IProfessor {
  id?: number;
  name?: string;
  department?: string;
  section?: ISection;
}

export class Professor implements IProfessor {
  constructor(public id?: number, public name?: string, public department?: string, public section?: ISection) {}
}
