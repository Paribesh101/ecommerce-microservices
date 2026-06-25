import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { OrderComponent } from './order/order.component';

export const routes: Routes = [
    {path : 'login', component: LoginComponent},
    {path : 'order', component: OrderComponent},
    {path: '', redirectTo: 'login', pathMatch: 'full'}
];
