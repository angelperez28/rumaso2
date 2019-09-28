import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICoursePage } from 'app/shared/model/course-page.model';

@Component({
  selector: 'jhi-course-page-detail',
  templateUrl: './course-page-detail.component.html'
})
export class CoursePageDetailComponent implements OnInit {
  coursePage: ICoursePage;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ coursePage }) => {
      this.coursePage = coursePage;
    });
  }

  previousState() {
    window.history.back();
  }
}
