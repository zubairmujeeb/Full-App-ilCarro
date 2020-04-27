
import {Component, OnDestroy, OnInit} from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import * as $ from 'jquery';
import {SearchFilters} from '../../models/search';
import {Car} from '../../models/car';
@Component({
  selector: 'app-main-results',
  templateUrl: './main-results.component.html',
  styleUrls: ['./main-results.component.scss']
})
export class MainResultsComponent implements OnInit, OnDestroy {

  filters: SearchFilters;
  cars: Car[];
  routerSubscription: any;

  constructor(private router: Router) {
    this.filters = new SearchFilters();
  }

  ngOnInit(): void {

    this.recallJsFuntions();
    const data = history.state.data;
    if (data !== undefined) {
      this.filters = data.filters;
      this.cars = data.cars;
      // tslint:disable-next-line: no-debugger
    }
  }

  recallJsFuntions() {
    this.routerSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(event => {
        // tslint:disable-next-line: no-debugger
        $.getScript('/../../../assets/js/main.js');
      });
  }

  // tslint:disable-next-line: use-lifecycle-interface
  ngOnDestroy() {
    this.routerSubscription.unsubscribe();
  }
}
