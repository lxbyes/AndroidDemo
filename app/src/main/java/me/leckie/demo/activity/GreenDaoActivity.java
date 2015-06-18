package me.leckie.demo.activity;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.Date;
import java.util.List;

import me.leckie.demo.R;
import me.leckie.demo.greendao.DaoMaster;
import me.leckie.demo.greendao.DaoSession;
import me.leckie.demo.greendao.Note;
import me.leckie.demo.greendao.NoteDao;

public class GreenDaoActivity extends ListActivity {

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private NoteDao noteDao;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        init();

        addNote();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //deleteAll();
    }

    public void init() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes.db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        noteDao = daoSession.getNoteDao();

        cursor = db.query(noteDao.getTablename(), noteDao.getAllColumns(), null, null, null, null, null);

        String[] from = {NoteDao.Properties.Text.columnName, NoteDao.Properties.Comment.columnName};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);
        setListAdapter(adapter);
    }

    public void addNote() {
        for (int i = 1; i < 6; i++) {
            String text = "notes txt " + i;
            String comment = "comment txt " + i;
            Note note = new Note(null, text, comment, new Date());
            noteDao.insert(note);
        }
        cursor.requery();
    }

    public void query() {
        List<Note> notes = noteDao.loadAll();
        StringBuffer sb = new StringBuffer("data:");
        for (Note note : notes) {
            sb.append(note.getId() + ",");
            sb.append(note.getText() + ",");
            sb.append(note.getComment() + ",");
            sb.append(note.getDate() + ",");
        }
    }

    public void deleteAll() {
        noteDao.deleteAll();
    }

}
