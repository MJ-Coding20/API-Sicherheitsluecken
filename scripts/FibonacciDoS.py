import asyncio
import aiohttp

repeats = 20
index = 100

# URL der Webseite, die aufgerufen werden soll
url = 'http://localhost:8001/api/fibonacci?index='+str(index)

async def fetch(url):
    async with aiohttp.ClientSession() as session:
        async with session.get(url) as response:
            html = await response.text()
            print(f"{url} -> {response.status}")
            return html

async def main():
    # Create all tasks without waiting
    tasks = [fetch(url) for i in range(repeats)]

    # Run them all at once
    results = await asyncio.gather(*tasks)

asyncio.run(main())

#for i in range(repeats):
#    print("start: "+str(i))
#    asyncio.run(fetch(url))