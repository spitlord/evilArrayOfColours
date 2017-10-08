package photoed.filters;

import photoed.Pic;

public interface Filter{
	Pic filter(Pic pic, String[] args);
}
