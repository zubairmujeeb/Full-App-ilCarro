import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';
import { TermsComponent } from './terms/terms.component';
import { AddCarComponent } from './add-car/add-car.component';
import { MainResultsComponent } from './main-results/main-results.component';



@NgModule({
  declarations: [TermsComponent, AddCarComponent, MainResultsComponent],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class ComponentsModule { }
