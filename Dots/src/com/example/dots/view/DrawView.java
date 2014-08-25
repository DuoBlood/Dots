package com.example.dots.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import com.example.dots.App;
import com.example.dots.R;
import com.example.dots.R.string;
import com.example.dots.model.Link;
import com.example.dots.model.MyCircle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DrawView extends View {

	private boolean firstStart = true;
	private int rows, columns;
	private int matrixSize = 0;
	private int count = 1;

	private Context context;
	private Paint p;
	private Path path;
	private HashMap<Integer, MyCircle> dotsMap;
	private ArrayList<Path> list_dots;
	private ArrayList<Path> list_check_green;
	private ArrayList<Path> list_check_blue;
	private ArrayList<Link> list_link;
	private ArrayList<HashSet<Integer>> list_set;

	public DrawView(Context context) {
		super(context);
		init();
		this.context = context;
	}

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		this.context = context;
	}

	public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
		this.context = context;
	}

	@SuppressLint("UseSparseArrays")
	private void init() {
		list_set = new ArrayList<HashSet<Integer>>();
		list_link = new ArrayList<Link>();
		list_dots = new ArrayList<Path>();
		list_check_green = new ArrayList<Path>();
		list_check_blue = new ArrayList<Path>();

		dotsMap = new HashMap<Integer, MyCircle>();

		p = new Paint();
		p.setStrokeWidth(5);
		p.setStyle(Paint.Style.FILL_AND_STROKE);
	}

	/**
	 * Метод установки размра матрицы.
	 * 
	 * @param rows_length
	 *            количество строк.
	 * @param columns_length
	 *            количество столбцов.
	 */
	public void setSize(int rows_length, int columns_length) {
		rows = rows_length;
		columns = columns_length;
		onlyDots();
	}

	public void onlyDots() {
		if (!firstStart) {
			init();
		}
		firstStart = false;
		path = new Path();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int myX = App.startX + App.step * j;
				int myY = App.startY + App.step * i;
				path.moveTo(myX, myY);
				path.addCircle(myX, myY, App.radius, Path.Direction.CW);
				dotsMap.put(count, new MyCircle(myX, myY));
				count++;
			}
		}
		matrixSize = count - 1;
		count = 1;
		path.close();
		list_dots.add(path);
//		adds();
		invalidate();
	}

	/**
	 * Метод добавления связей при старте.
	 */
	private void adds() {
		add(1, 2);
		add(3, 2);
		add(1, 6);
		add(11, 12);
		add(12, 13);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.parseColor("#E4F4E4"));
		buildSets();
		// рисование точек
		for (Path path : list_dots) {
			p.setColor(Color.RED);
			canvas.drawPath(path, p);
		}
		// рисование связей
		for (Link link : list_link) {
			p.setColor(Color.RED);
			canvas.drawPath(link.getPath(), p);
		}
		// рисование проверки связывания
		// если в связаны
		for (Path path : list_check_green) {
			p.setColor(Color.GREEN);
			canvas.drawPath(path, p);
		}
		// если не связаны
		for (Path path : list_check_blue) {
			p.setColor(Color.BLUE);
			canvas.drawPath(path, p);
		}
	}

	/**
	 * Метод добавления связи между двумя соседними точками.
	 * 
	 * @param n1
	 *            номер точки
	 * @param n2
	 *            номер соседней точки
	 * @param key
	 *            ключ связи
	 */
	public void add(int n1, int n2) {
		if (n1 == 0 || n2 == 0) {
			Toast.makeText(context, context.getString(R.string.must_be_more),
					Toast.LENGTH_SHORT).show();
			return;
		} else if (n1 > matrixSize || n2 > matrixSize) {
			Toast.makeText(context,
					context.getString(R.string.dot_doesnt_exist),
					Toast.LENGTH_SHORT).show();
			return;
		}

		Link link = new Link(n1, n2, dotsMap);

		int x1 = link.getDot1().getX();
		int y1 = link.getDot1().getY();
		int x2 = link.getDot2().getX();
		int y2 = link.getDot2().getY();
		// Проверка является ли точка соседней
		if (((x1 == x2 + App.step || x1 == x2 - App.step) && y1 == y2)
				|| (y1 == y2 + App.step || y1 == y2 - App.step) && x1 == x2) {
			list_link.add(link);
			invalidate();
		} else if (x1 == x2 && y1 == y2) {
			Toast.makeText(context,
					context.getString(R.string.dots_must_be_different),
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context,
					context.getString(R.string.dots_must_be_near),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Поиск точки в других множествах
	 * 
	 * @param n
	 *            точка
	 * @param set
	 *            множество
	 */
	private void findDot(int n, HashSet<Integer> set) {
		boolean isFind;
		int count = 1;
		for (Iterator<HashSet<Integer>> iter = list_set.iterator(); iter
				.hasNext();) {
			isFind = false;
			HashSet<Integer> mySet = iter.next();
			for (Integer item : mySet) {
				if (item == n && !isFind) {
					isFind = true;
					break;
				}
			}
			if (isFind) {
				set.addAll(mySet);
				iter.remove();
			}
			if (count == list_set.size()) {
				set.add(n);
			}
			count++;
		}
	}

	/**
	 * Метод удаления связи между двумя соседними точками.
	 * 
	 * @param n1
	 *            номер точки
	 * @param n2
	 *            номер соседней точки
	 * @param key
	 *            ключ связи
	 */
	public void delete(int n1, int n2) {
		if (n1 == 0 || n2 == 0) {
			Toast.makeText(context, context.getString(R.string.must_be_more),
					Toast.LENGTH_SHORT).show();
			return;
		}
		Link link = new Link(n1, n2, dotsMap);
		list_link.remove(link);
		invalidate();
	}

	/**
	 * Метод проверки связи между двумя элементами матрицы.
	 * 
	 * @param n1
	 *            номер точки
	 * @param n2
	 *            номер соседней точки
	 */
	public void check(int n1, int n2) {
		if (n1 == 0 || n2 == 0) {
			Toast.makeText(context, context.getString(R.string.must_be_more),
					Toast.LENGTH_SHORT).show();
			return;
		}
		MyCircle d1 = dotsMap.get(n1);
		MyCircle d2 = dotsMap.get(n2);

		int x1 = d1.getX();
		int y1 = d1.getY();
		int x2 = d2.getX();
		int y2 = d2.getY();

		path = new Path();
		path.addCircle(x1, y1, App.radius, Path.Direction.CW);
		path.addCircle(x2, y2, App.radius, Path.Direction.CW);

		if (isTogether(n1, n2)) {
			list_check_green = new ArrayList<Path>();
			list_check_blue = new ArrayList<Path>();
			list_check_green.add(path);
		} else {
			list_check_blue = new ArrayList<Path>();
			list_check_green = new ArrayList<Path>();
			list_check_blue.add(path);
		}
		invalidate();
	}

	private boolean isTogether(int n1, int n2) {
		boolean find_n1;
		boolean find_n2;
		for (HashSet<Integer> set : list_set) {
			find_n1 = false;
			find_n2 = false;
			for (Integer item : set) {
				if (!find_n1 && item == n1) {
					find_n1 = true;
				}
				if (!find_n2 && item == n2) {
					find_n2 = true;
				}
			}
			if (find_n1 && find_n2) {
				return true;
			}
		}
		return false;
	}

	private void buildSets() {
		list_set = new ArrayList<>();
		for (Link it : list_link) {
			int n1 = it.getDotN1();
			int n2 = it.getDotN2();
			if (list_set.size() > 0) {
				for (Iterator<HashSet<Integer>> iter = list_set.iterator(); iter
						.hasNext();) {
					boolean isFindFirst = false;
					boolean isFindSecond = false;

					HashSet<Integer> set = iter.next();

					for (Integer item : set) {
						if (item == n1 && !isFindFirst) {
							isFindFirst = true;
						}
						if (item == n2 && !isFindSecond) {
							isFindSecond = true;
						}
					}
					if (isFindFirst && !isFindSecond) {
						findDot(n2, set);
						count = 1;
						break;
					} else if (!isFindFirst && isFindSecond) {
						findDot(n1, set);
						count = 1;
						break;
					}

					if (count == list_set.size()) {
						HashSet<Integer> mySet = new HashSet<Integer>();
						mySet.add(n1);
						mySet.add(n2);
						list_set.add(mySet);
						count = 1;
						break;
					}
					count++;
				}
			} else {
				HashSet<Integer> mySet = new HashSet<Integer>();
				mySet.add(n1);
				mySet.add(n2);
				list_set.add(mySet);
			}
		}
	}
}
