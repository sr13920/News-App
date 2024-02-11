import { Routes } from '@angular/router';
import path from 'node:path';
import { LoginComponent } from './pages/login/login.component';;
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AuthGuard } from './auth.guard';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { WishlistComponent } from './pages/wishlist/wishlist.component';

export const routes: Routes = [
    {
        path:'', redirectTo:'login',pathMatch:'full'
    },
    {
        path:'login',
        component: LoginComponent
    },
    {
                path:'dashboard',
                component:DashboardComponent,
                canActivate: [AuthGuard]
          
    },
    {
        path:'profile',
        component:UserProfileComponent,
        canActivate: [AuthGuard]
    },
    {
        path:'wishlist',
        component:WishlistComponent,
       canActivate: [AuthGuard]
    }
];
