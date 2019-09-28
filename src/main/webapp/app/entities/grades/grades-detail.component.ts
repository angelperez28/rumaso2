import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrades } from 'app/shared/model/grades.model';

@Component({
  selector: 'jhi-grades-detail',
  templateUrl: './grades-detail.component.html'
})
export class GradesDetailComponent implements OnInit {
  grades: IGrades;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ grades }) => {
      this.grades = grades;
    });
  }

  previousState() {
    window.history.back();
  }
}
