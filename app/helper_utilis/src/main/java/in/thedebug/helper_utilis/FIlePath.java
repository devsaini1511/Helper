package in.thedebug.helper_utilis;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;

public class FIlePath {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {


        Log.d(" File -",
                "Authority: " + uri.getAuthority() +
                        ", Fragment: " + uri.getFragment() +
                        ", Port: " + uri.getPort() +
                        ", Query: " + uri.getQuery() +
                        ", Scheme: " + uri.getScheme() +
                        ", Host: " + uri.getHost() +
                        ", Segments: " + uri.getPathSegments().toString()
        );

        // DocumentProvider
        if (isDocumentUri(context, uri)) {
            // LocalStorageProvider
            if (isLocalStorageDocument()) {
                // The path is the id
                return getDocumentId(uri);
            }
            // ExternalStorageProvider
            else if (isExternalStorageDocument(uri)) {
                final String docId = getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // Handle SD cards
                File file = getFileIfExists("/storage/extSdCard", split[1]);
                if (file != null) {
                    return file.getAbsolutePath();
                }
                file = getFileIfExists("/storage/sdcard1", split[1]);
                if (file != null) {
                    return file.getAbsolutePath();
                }
                file = getFileIfExists("/storage/usbcard1", split[1]);
                if (file != null) {
                    return file.getAbsolutePath();
                }
                file = getFileIfExists("/storage/sdcard0", split[1]);
                if (file != null) {
                    return file.getAbsolutePath();
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = getDocumentId(uri);

                String rawPrefix = "raw:";
                if (id.startsWith(rawPrefix)) {
                    return id.substring(rawPrefix.length());
                }

                try {
                    String[] contentUriPrefixesToTry = new String[]{
                            "content://downloads/public_downloads",
                            "content://downloads/my_downloads",
                            "content://downloads/all_downloads"
                    };


                    for (String contentUriPrefix : contentUriPrefixesToTry) {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.parseLong(id));
                        try {
                            String path = getDataColumn(context, contentUri, null, null);
                            if (path != null) {
                                return path;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                String displayName = getDisplayNameColumn(context, uri);
                if (displayName != null) {
                    File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), displayName);
                    if (file.exists()) {
                        return file.getAbsolutePath();
                    }
                }

            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }

        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean isDocumentUri(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String getDocumentId(Uri documentUri) {
        return DocumentsContract.getDocumentId(documentUri);
    }

    private static boolean isLocalStorageDocument() {
//        return LocalStorageProvider.AUTHORITY.equals(uri.getAuthority());
        return false;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author paulburke
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author paulburke
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static File getFileIfExists(String basePath, String path) {
        File result = null;
        File file = new File(basePath);
        if (file.exists()) {
            file = new File(file, path);
            if (file.exists()) {
                result = file;
            }
        }
        return result;
    }

    private static String getDisplayNameColumn(Context context, Uri uri) {
        return getColumn(context, uri, DocumentsContract.Document.COLUMN_DISPLAY_NAME, null, null);
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        return getColumn(context, uri, MediaStore.Files.FileColumns.DATA, selection, selectionArgs);
    }

    private static String getColumn(Context context, Uri uri, String column, String selection,
                                    String[] selectionArgs) {

        final String[] projection = {
                column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {

                DatabaseUtils.dumpCursor(cursor);

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
