using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace SmartMarket
{
    public class VersionTokenizer
    {
        private String _versionString;
        private int _length;
        private int _position;
        private int _number;
        private String _suffix;
        public VersionTokenizer(String versionString)
        {
            _versionString = versionString;
            _length = versionString.Count();
        }
        public int getNumber()
        {
            return _number;
        }
        public String getSuffix()
        {
            return _suffix;
        }
        public bool MoveNext()
        {
            _number = 0;
            _suffix = "";
            // No more characters
            if (_position >= _length)
            {
                return false;
            }
            while (_position < _length)
            {
                char c = _versionString[_position];
                if (c < '0' || c > '9') break;
                _number = _number * 10 + (c - '0');
                _position++;
            }
            int suffixStart = _position;
            while (_position < _length)
            {
                char c = _versionString[_position];
                if (c == '.') break;
                _position++;
            }
            _suffix = _versionString.Substring(suffixStart, _position - suffixStart);
            if (_position < _length)
            {
                _position++;
            }
            return true;
        }
    }
}