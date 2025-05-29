package com.rockettest.biblioetca.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import java.beans.PropertyDescriptor;

public class HtmlSanitizer {
  public static String stripHtml(String html) {
    if (html == null) return null;
    return Jsoup.clean(html, Safelist.none());
  }

  public static <T> T stripHtml(T obj) {
    BeanWrapper wrapper = new BeanWrapperImpl(obj);
    for (PropertyDescriptor pd : wrapper.getPropertyDescriptors()) {
      String name = pd.getName();
      if ("class".equals(name)) {
        continue;
      }
      if (pd.getPropertyType() == String.class && wrapper.isWritableProperty(name)) {
        String original = (String) wrapper.getPropertyValue(name);
        String safe = (original == null)
                      ? null
                      : Jsoup.clean(original, Safelist.none());
        wrapper.setPropertyValue(name, safe);
      }
    }
    return obj;
  }
}
