import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { RegisterComponent } from './components/register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {ComponentsModule} from './components/components.module';
import {MostPopularCarsComponent} from './components/most-popular-cars/most-popular-cars.component';
import {LoginComponent} from './components/login/login.component';
import {ProfileComponent} from './components/profile/profile.component';
import {SettingsComponent} from './components/profile/settings/settings.component';
import {PromoBannerComponent} from './components/promo-banner/promo-banner.component';
import {LastFeedsComponent} from './components/last-feeds/last-feeds.component';
import {MainComponent} from './components/main/main.component';
import {DatePipe} from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    ProfileComponent,
    SettingsComponent,
    MostPopularCarsComponent,
    PromoBannerComponent,
    LastFeedsComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ComponentsModule
  ],
  providers: [DatePipe],
  exports: [
    MostPopularCarsComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
