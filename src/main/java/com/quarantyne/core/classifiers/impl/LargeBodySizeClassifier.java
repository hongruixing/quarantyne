package com.quarantyne.core.classifiers.impl;

import com.quarantyne.core.classifiers.HttpRequestClassifier;
import com.quarantyne.core.classifiers.Label;
import com.quarantyne.core.lib.HttpRequest;
import com.quarantyne.core.lib.HttpRequestBody;
import java.util.Set;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LargeBodySizeClassifier implements HttpRequestClassifier {

  protected static int MAX_SIZE_BYTES = 1_024 * 1000;

  @Override
  public Set<Label> classify(final HttpRequest httpRequest, final HttpRequestBody body) {
    if (body == null) {
      return EMPTY_LABELS;
    }
    if (body.getBody().length > MAX_SIZE_BYTES) {
      if (log.isDebugEnabled()) {
        log.debug("large body size: {} bytes", body.getBody().length);
      }
      return Label.LARGE_BODY;
    }
    return EMPTY_LABELS;
  }

  @Override
  public boolean test(HttpRequest httpRequest, @Nullable HttpRequestBody body) {
    return isWriteRequest(httpRequest) && hasBody(body);
  }
}
