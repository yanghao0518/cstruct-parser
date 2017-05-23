package com.haoyu.module.jcstruct.resolve;

import java.io.IOException;

import com.haoyu.module.jcstruct.exception.DtuMessageException;
import com.haoyu.module.jcstruct.model.ResolveResult;

public interface DefaultResolve extends Resolve
{
	public <T> ResolveResult<T> resolve(byte[] data,ResolveResultRowMapper<T> rowMapper) throws DtuMessageException, IOException;

}
