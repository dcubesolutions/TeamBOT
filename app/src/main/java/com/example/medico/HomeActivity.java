package com.example.medico;

        import android.content.Intent;
        import android.os.Bundle;
        import com.google.android.material.floatingactionbutton.FloatingActionButton;

        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentTransaction;
        import android.view.View;
        import com.google.android.material.navigation.NavigationView;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;
        import androidx.appcompat.app.ActionBarDrawerToggle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
      ft.replace(R.id.frmLyt,new homeFrag());
        ft.commit();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,newPost.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_home) {
           ft.replace(R.id.frmLyt,new homeFrag());
            ft.commit();
        } else if (id == R.id.nav_chat) {
            startActivity(new Intent(HomeActivity.this,ChatActivity.class));

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(HomeActivity.this,settings.class));
            ft.commit();
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logut) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this,LogIn.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(R.string.post_title);
    }
}
