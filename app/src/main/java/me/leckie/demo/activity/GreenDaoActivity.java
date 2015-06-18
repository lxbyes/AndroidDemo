package me.leckie.demo.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import me.leckie.demo.R;
import me.leckie.demo.greendao.DaoMaster;
import me.leckie.demo.greendao.DaoSession;
import me.leckie.demo.greendao.Note;
import me.leckie.demo.greendao.NoteDao;

public class GreenDaoActivity extends Activity {

    private TextView mTextView;

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        init();

        addNote();

        query();
    }

    public void init() {
        mTextView = (TextView) findViewById(R.id.tv);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes.db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        noteDao = daoSession.getNoteDao();
    }

    public void addNote() {
        for (int i = 0; i < 10; i++) {
            String text = "notes txt " + i;
            String comment = "comment txt " + i;
            Note note = new Note(null, text, comment, new Date());
            noteDao.insert(note);
        }
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
        mTextView.setText(sb);
    }

}
