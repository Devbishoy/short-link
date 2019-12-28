import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ShortLinkSharedModule } from 'app/shared/shared.module';
import { ShortLinkCoreModule } from 'app/core/core.module';
import { ShortLinkAppRoutingModule } from './app-routing.module';
import { ShortLinkHomeModule } from './home/home.module';
import { ShortLinkEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ShortLinkSharedModule,
    ShortLinkCoreModule,
    ShortLinkHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ShortLinkEntityModule,
    ShortLinkAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class ShortLinkAppModule {}
