package com.btc.common.extension.view.listView.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import lombok.val;

import com.btc.common.contract.Contracts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArrayAdapter<T> extends BaseAdapter implements Filterable {

    @NonNull
    public static ArrayAdapter<CharSequence> createFromResource(
        @NonNull final Context context,
        @ArrayRes final int textArrayResId,
        @LayoutRes final int textViewResId) {
        Contracts.requireNonNull(context, "context == null");

        final val strings = context.getResources().getTextArray(textArrayResId);
        return new ArrayAdapter<>(context, textViewResId, strings);
    }

    public ArrayAdapter(@NonNull final Context context, @LayoutRes final int resource) {
        this(Contracts.requireNonNull(context, "context == null"), resource, 0, new ArrayList<T>());
    }

    public ArrayAdapter(
        @NonNull final Context context,
        @LayoutRes final int resource,
        @IdRes final int textViewResourceId) {
        this(Contracts.requireNonNull(context, "context == null"),
             resource,
             textViewResourceId,
             new ArrayList<T>());
    }

    public ArrayAdapter(
        @NonNull final Context context, @LayoutRes final int resource, @NonNull final T[] objects) {
        this(Contracts.requireNonNull(context, "context == null"),
             resource,
             0,
             Arrays.asList(Contracts.requireNonNull(objects, "objects == null")));
    }

    public ArrayAdapter(
        @NonNull final Context context,
        @LayoutRes final int resource,
        @IdRes final int textViewResourceId,
        @NonNull final T[] objects) {
        this(Contracts.requireNonNull(context, "context == null"),
             resource,
             textViewResourceId,
             Arrays.asList(Contracts.requireNonNull(objects, "objects == null")));
    }

    public ArrayAdapter(
        @NonNull final Context context,
        @LayoutRes final int resource,
        @NonNull final List<T> objects) {
        this(Contracts.requireNonNull(context, "context == null"),
             resource,
             0,
             Contracts.requireNonNull(objects, "objects == null"));
    }

    public ArrayAdapter(
        @NonNull final Context context,
        @LayoutRes final int resource,
        @IdRes final int textViewResourceId,
        @NonNull final List<T> objects) {
        Contracts.requireNonNull(context, "context == null");
        Contracts.requireNonNull(objects, "objects == null");

        _context = context;
        _inflater = LayoutInflater.from(context);
        _resource = _dropDownResource = resource;
        _objects = objects;
        _fieldId = textViewResourceId;
    }

    public void add(@Nullable final T object) {
        synchronized (_lock) {
            if (_originalValues != null) {
                _originalValues.add(object);
            } else {
                _objects.add(object);
            }
        }
        if (_notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void addAll(@NonNull final Collection<? extends T> collection) {
        Contracts.requireNonNull(collection, "collection == null");

        synchronized (_lock) {
            if (_originalValues != null) {
                _originalValues.addAll(collection);
            } else {
                _objects.addAll(collection);
            }
        }
        if (_notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void addAll(final T... items) {
        synchronized (_lock) {
            if (_originalValues != null) {
                Collections.addAll(_originalValues, items);
            } else {
                Collections.addAll(_objects, items);
            }
        }
        if (_notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        synchronized (_lock) {
            if (_originalValues != null) {
                _originalValues.clear();
            } else {
                _objects.clear();
            }
        }
        if (_notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    @NonNull
    public Context getContext() {
        return _context;
    }

    @Override
    public int getCount() {
        return _objects.size();
    }

    @Nullable
    @Override
    public T getItem(final int position) {
        return _objects.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(
        final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        return createViewFromResource(_inflater, position, convertView, parent, _resource);
    }

    @Nullable
    public Resources.Theme getDropDownViewTheme() {
        return _dropDownInflater == null ? null : _dropDownInflater.getContext().getTheme();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setDropDownViewTheme(@Nullable final Resources.Theme theme) {
        if (theme == null) {
            _dropDownInflater = null;
        } else if (theme == _inflater.getContext().getTheme()) {
            _dropDownInflater = _inflater;
        } else {
            final val context = new ContextThemeWrapper(_context, theme);
            _dropDownInflater = LayoutInflater.from(context);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (_filter == null) {
            _filter = new ArrayFilter();
        }
        return _filter;
    }

    public int getPosition(@Nullable final T item) {
        return _objects.indexOf(item);
    }

    public void insert(@Nullable final T object, final int index) {
        synchronized (_lock) {
            if (_originalValues != null) {
                _originalValues.add(index, object);
            } else {
                _objects.add(index, object);
            }
        }
        if (_notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        _notifyOnChange = true;
    }

    @Override
    public View getDropDownView(
        final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        final val inflater = _dropDownInflater == null ? _inflater : _dropDownInflater;
        return createViewFromResource(inflater, position, convertView, parent, _dropDownResource);
    }

    public void remove(@Nullable final T object) {
        synchronized (_lock) {
            if (_originalValues != null) {
                _originalValues.remove(object);
            } else {
                _objects.remove(object);
            }
        }
        if (_notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void setDropDownViewResource(@LayoutRes final int resource) {
        this._dropDownResource = resource;
    }

    public void setNotifyOnChange(final boolean notifyOnChange) {
        _notifyOnChange = notifyOnChange;
    }

    public void sort(@NonNull final Comparator<? super T> comparator) {
        synchronized (_lock) {
            if (_originalValues != null) {
                Collections.sort(_originalValues, comparator);
            } else {
                Collections.sort(_objects, comparator);
            }
        }
        if (_notifyOnChange) {
            notifyDataSetChanged();
        }
    }

    @Nullable
    protected CharSequence getItemName(@Nullable final T item) {
        final CharSequence name;

        if (item instanceof CharSequence) {
            name = (CharSequence) item;
        } else {
            name = item == null ? null : item.toString();
        }

        return name;
    }

    private final Context _context;

    private final LayoutInflater _inflater;

    private final Object _lock = new Object();

    private final int _resource;

    private LayoutInflater _dropDownInflater;

    private int _dropDownResource;

    private int _fieldId = 0;

    private ArrayFilter _filter;

    private boolean _notifyOnChange = true;

    private List<T> _objects;

    private ArrayList<T> _originalValues;

    @NonNull
    private View createViewFromResource(
        @NonNull final LayoutInflater inflater,
        final int position,
        @Nullable final View convertView,
        @NonNull final ViewGroup parent,
        final int resource) {
        final View view;
        final TextView text;

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }

        try {
            if (_fieldId == 0) {
                //  If no custom field is assigned, assume the whole resource is a TextView
                text = (TextView) view;
            } else {
                //  Otherwise, find the TextView field within the layout
                text = (TextView) view.findViewById(_fieldId);

                if (text == null) {
                    throw new RuntimeException("Failed to find view with ID " +
                                               _context.getResources().getResourceName(_fieldId) +
                                               " in item layout");
                }
            }
        } catch (final ClassCastException e) {
            Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException("ArrayAdapter requires the resource ID to be a " +
                                            "TextView",
                                            e);
        }

        final val item = getItem(position);
        text.setText(getItemName(item));

        return view;
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(final CharSequence prefix) {
            final val results = new FilterResults();

            if (_originalValues == null) {
                synchronized (_lock) {
                    _originalValues = new ArrayList<>(_objects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<T> list;
                synchronized (_lock) {
                    list = new ArrayList<>(_originalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<T> values;
                synchronized (_lock) {
                    values = new ArrayList<>(_originalValues);
                }

                final int count = values.size();
                final val newValues = new ArrayList<T>();

                for (int i = 0; i < count; i++) {
                    final val value = values.get(i);
                    final val itemName = getItemName(value);
                    final val valueText = itemName == null ? "" : itemName.toString().toLowerCase();

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final val words = valueText.split(" ");
                        for (final val word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(final CharSequence constraint, final FilterResults results) {
            //noinspection unchecked
            _objects = (List<T>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
