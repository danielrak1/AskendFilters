import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule} from '@angular/forms';

import { AppComponent } from './app.component';
import { FilterDialogComponent } from './components/filter-dialog/filter-dialog.component';
import { FilterListComponent } from './components/filter-list/filter-list.component';
import { ConditionLabelPipe } from './pipes/condition-label.pipe';

@NgModule({
  declarations: [
    AppComponent,
    FilterDialogComponent,
    FilterListComponent,
    ConditionLabelPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
