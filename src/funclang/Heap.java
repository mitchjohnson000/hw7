package funclang;

import funclang.Value.RefVal;

public interface Heap {
	
public Value.RefVal ref(Value value);
public Value deref(Value.RefVal refVal);
public Value setref(Value.RefVal refVal,Value value);
public Value.RefVal free(Value.RefVal refVal);

static class Heap16Bit implements Heap{
	private Value[] _rep;
	private int l;

	@Override
	public RefVal ref(Value value) {
		
		if(_rep == null){
			l=0;
			_rep = new Value[10];
			} 
		if(_rep.length == Math.pow(2, 16)){
			//return new Value.DynamicError("Heap is larger than 2^16 in size, please deallocate memory");
			}
		if(l == _rep.length){
			Value[] temp = new Value[_rep.length * 2];
			for(int i=0;i<_rep.length;i++){
				temp[i] = _rep[i];
			}
			_rep = temp;
			}
		_rep[l] = value;
		l++;
		return new Value.RefVal(l-1);
	}

	@Override
	public Value deref(RefVal refVal) {
		return _rep[refVal.getLoc()];
		
	}

	@Override
	public Value setref(RefVal refVal, Value value) {
		_rep[refVal.getLoc()] = value;
		return value;		
	}

	@Override
	public Value.RefVal free(RefVal refVal) {
		 Value[] n = new Value[_rep.length - 1];
		 System.arraycopy(_rep, 0, n, 0, refVal.getLoc() );
		 System.arraycopy(_rep, refVal.getLoc()+1, n, refVal.getLoc(), _rep.length - refVal.getLoc()-1);
		 _rep = n;
		 return refVal;
	}
	
}



}
