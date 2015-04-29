package sit.khaycake.model;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;
import sit.khaycake.database.SQL;
import sit.khaycake.database.exception.ColumnValueMismatchException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Falook Glico on 4/26/2015.
 */
public class Picture extends Model implements ORM {

    private int id;
    private String title;
    private String filename;

    public static final String TABLE_NAME = "PICTURES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME,"PIC_ID");
    public static final Column COLUMN_TITLE = ORM.column(TABLE_NAME,"TITLE");
    public static final Column COLUMN_FILENAME = ORM.column(TABLE_NAME,"FILENAME");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql.insert().into(TABLE_NAME, COLUMN_TITLE, COLUMN_FILENAME)
                .values(this.title, this.filename)
                .exec();
        this.id =id;
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql.update(TABLE_NAME)
                .set(COLUMN_TITLE, this.title)
                .where(COLUMN_ID, SQL.WhereClause.Operator.EQ, this.id)
                .exec();
    }

    public void delete() throws Exception {
        SQL sql = new SQL();
        sql.delete(TABLE_NAME)
                .where(COLUMN_ID, SQL.WhereClause.Operator.EQ, this.id)
                .exec();
    }

    @Override
    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setFilename(rs.getString(COLUMN_FILENAME.getColumnName()));
        this.setTitle(rs.getString(COLUMN_TITLE.getColumnName()));
    }

    public static List<Picture> findByProduct(int prodId) throws Exception {

        SQL sql = new SQL();
        List<PicProduct> pics = sql.select()
                .from(PicProduct.TABLE_NAME)
                .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, prodId)
                .fetch(PicProduct.class);

        List<Picture> pictures = null;
        if(pics != null){
            for(PicProduct p : pics)
            {
                if(pictures == null)
                    pictures = new ArrayList<>();
                pictures.add(p.getPicture());
            }
        }


        return pictures;
    }


}
