package com.haoyu.module.jcstruct.resolve;

import java.io.IOException;
import java.io.InputStream;

import com.haoyu.module.jcstruct.exception.DtuMessageException;

public interface SocketResolve extends Resolve
{
	public <T> T resolve(InputStream is,ResolveResultRowMapper<T> rowMapper) throws DtuMessageException, IOException;
}
