import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { SettingsComponent } from './components/profile/settings/settings.component';
import {AuthGuardGuard} from './services/guards/auth-guard.guard';
import {AuthBlockGuard} from './services/guards/auth-block.guard';
import {MainComponent} from './components/main/main.component';
import {TermsComponent} from './components/terms/terms.component';
import {AddCarComponent} from './components/add-car/add-car.component';
import {MainResultsComponent} from './components/main-results/main-results.component';


const routes: Routes = [
  { path: '', component: MainComponent },
  { path: 'main', component: MainComponent },
  {path: 'results', component: MainResultsComponent},
  { path: 'terms', component: TermsComponent},
  { path: 'car', component: AddCarComponent} ,
  { path: 'registration', component: RegisterComponent, canActivate: [AuthBlockGuard] },
  { path: 'login', component: LoginComponent, canActivate: [AuthBlockGuard] },
  { path: 'profile', component: ProfileComponent , canActivate: [AuthGuardGuard] },
  { path: 'profile/settings', component: SettingsComponent, canActivate: [AuthGuardGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
