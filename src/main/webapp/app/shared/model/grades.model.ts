import { ISection } from 'app/shared/model/section.model';

export interface IGrades {
  id?: number;
  aNumber?: number;
  bNumber?: number;
  cNumber?: number;
  dNumber?: number;
  fNumber?: number;
  wNumber?: number;
  studentNumber?: number;
  section?: ISection;
}

export class Grades implements IGrades {
  constructor(
    public id?: number,
    public aNumber?: number,
    public bNumber?: number,
    public cNumber?: number,
    public dNumber?: number,
    public fNumber?: number,
    public wNumber?: number,
    public studentNumber?: number,
    public section?: ISection
  ) {}
}
